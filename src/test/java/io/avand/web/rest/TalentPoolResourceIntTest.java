package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.TalentPoolEntity;
import io.avand.repository.TalentPoolRepository;
import io.avand.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.avand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.avand.domain.enumeration.CandidateState;
/**
 * Test class for the TalentPoolEntityResource REST controller.
 *
 * @see TalentPoolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class TalentPoolResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_ID = 1L;
    private static final Long UPDATED_FILE_ID = 2L;

    private static final CandidateState DEFAULT_STATE = CandidateState.ACCEPTED;
    private static final CandidateState UPDATED_STATE = CandidateState.REJECTED;

    private static final String DEFAULT_CELLPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELLPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private TalentPoolRepository talentPoolRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTalentPoolEntityMockMvc;

    private TalentPoolEntity talentPoolEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TalentPoolResource talentPoolResource = new TalentPoolResource(talentPoolRepository);
        this.restTalentPoolEntityMockMvc = MockMvcBuilders.standaloneSetup(talentPoolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TalentPoolEntity createEntity(EntityManager em) {
        TalentPoolEntity talentPoolEntity = new TalentPoolEntity()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fileId(DEFAULT_FILE_ID)
            .state(DEFAULT_STATE)
            .cellphone(DEFAULT_CELLPHONE)
            .email(DEFAULT_EMAIL);
        return talentPoolEntity;
    }

    @Before
    public void initTest() {
        talentPoolEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalentPoolEntity() throws Exception {
        int databaseSizeBeforeCreate = talentPoolRepository.findAll().size();

        // Create the TalentPoolEntity
        restTalentPoolEntityMockMvc.perform(post("/api/talent-pool-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talentPoolEntity)))
            .andExpect(status().isCreated());

        // Validate the TalentPoolEntity in the database
        List<TalentPoolEntity> talentPoolEntityList = talentPoolRepository.findAll();
        assertThat(talentPoolEntityList).hasSize(databaseSizeBeforeCreate + 1);
        TalentPoolEntity testTalentPoolEntity = talentPoolEntityList.get(talentPoolEntityList.size() - 1);
        assertThat(testTalentPoolEntity.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTalentPoolEntity.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTalentPoolEntity.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testTalentPoolEntity.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testTalentPoolEntity.getCellphone()).isEqualTo(DEFAULT_CELLPHONE);
        assertThat(testTalentPoolEntity.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createTalentPoolEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = talentPoolRepository.findAll().size();

        // Create the TalentPoolEntity with an existing ID
        talentPoolEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalentPoolEntityMockMvc.perform(post("/api/talent-pool-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talentPoolEntity)))
            .andExpect(status().isBadRequest());

        // Validate the TalentPoolEntity in the database
        List<TalentPoolEntity> talentPoolEntityList = talentPoolRepository.findAll();
        assertThat(talentPoolEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTalentPoolEntities() throws Exception {
        // Initialize the database
        talentPoolRepository.saveAndFlush(talentPoolEntity);

        // Get all the talentPoolEntityList
        restTalentPoolEntityMockMvc.perform(get("/api/talent-pool-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talentPoolEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].cellphone").value(hasItem(DEFAULT_CELLPHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getTalentPoolEntity() throws Exception {
        // Initialize the database
        talentPoolRepository.saveAndFlush(talentPoolEntity);

        // Get the talentPoolEntity
        restTalentPoolEntityMockMvc.perform(get("/api/talent-pool-entities/{id}", talentPoolEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(talentPoolEntity.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.cellphone").value(DEFAULT_CELLPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTalentPoolEntity() throws Exception {
        // Get the talentPoolEntity
        restTalentPoolEntityMockMvc.perform(get("/api/talent-pool-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalentPoolEntity() throws Exception {
        // Initialize the database
        talentPoolRepository.saveAndFlush(talentPoolEntity);
        int databaseSizeBeforeUpdate = talentPoolRepository.findAll().size();

        // Update the talentPoolEntity
        TalentPoolEntity updatedTalentPoolEntity = talentPoolRepository.findOne(talentPoolEntity.getId());
        // Disconnect from session so that the updates on updatedTalentPoolEntity are not directly saved in db
        em.detach(updatedTalentPoolEntity);
        updatedTalentPoolEntity
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fileId(UPDATED_FILE_ID)
            .state(UPDATED_STATE)
            .cellphone(UPDATED_CELLPHONE)
            .email(UPDATED_EMAIL);

        restTalentPoolEntityMockMvc.perform(put("/api/talent-pool-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTalentPoolEntity)))
            .andExpect(status().isOk());

        // Validate the TalentPoolEntity in the database
        List<TalentPoolEntity> talentPoolEntityList = talentPoolRepository.findAll();
        assertThat(talentPoolEntityList).hasSize(databaseSizeBeforeUpdate);
        TalentPoolEntity testTalentPoolEntity = talentPoolEntityList.get(talentPoolEntityList.size() - 1);
        assertThat(testTalentPoolEntity.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTalentPoolEntity.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTalentPoolEntity.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testTalentPoolEntity.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTalentPoolEntity.getCellphone()).isEqualTo(UPDATED_CELLPHONE);
        assertThat(testTalentPoolEntity.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingTalentPoolEntity() throws Exception {
        int databaseSizeBeforeUpdate = talentPoolRepository.findAll().size();

        // Create the TalentPoolEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTalentPoolEntityMockMvc.perform(put("/api/talent-pool-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talentPoolEntity)))
            .andExpect(status().isCreated());

        // Validate the TalentPoolEntity in the database
        List<TalentPoolEntity> talentPoolEntityList = talentPoolRepository.findAll();
        assertThat(talentPoolEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTalentPoolEntity() throws Exception {
        // Initialize the database
        talentPoolRepository.saveAndFlush(talentPoolEntity);
        int databaseSizeBeforeDelete = talentPoolRepository.findAll().size();

        // Get the talentPoolEntity
        restTalentPoolEntityMockMvc.perform(delete("/api/talent-pool-entities/{id}", talentPoolEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TalentPoolEntity> talentPoolEntityList = talentPoolRepository.findAll();
        assertThat(talentPoolEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalentPoolEntity.class);
        TalentPoolEntity talentPoolEntity1 = new TalentPoolEntity();
        talentPoolEntity1.setId(1L);
        TalentPoolEntity talentPoolEntity2 = new TalentPoolEntity();
        talentPoolEntity2.setId(talentPoolEntity1.getId());
        assertThat(talentPoolEntity1).isEqualTo(talentPoolEntity2);
        talentPoolEntity2.setId(2L);
        assertThat(talentPoolEntity1).isNotEqualTo(talentPoolEntity2);
        talentPoolEntity1.setId(null);
        assertThat(talentPoolEntity1).isNotEqualTo(talentPoolEntity2);
    }
}
