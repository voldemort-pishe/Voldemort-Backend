package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.repository.jpa.CandidateScheduleRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.avand.web.rest.TestUtil.sameInstant;
import static io.avand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CandidateScheduleEntityResource REST controller.
 *
 * @see CandidateScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class CandidateScheduleResourceIntTest {

    private static final Long DEFAULT_OWNER = 1L;
    private static final Long UPDATED_OWNER = 2L;

    private static final ZonedDateTime DEFAULT_SCHEDULE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SCHEDULE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CandidateScheduleRepository candidateScheduleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidateScheduleEntityMockMvc;

    private CandidateScheduleEntity candidateScheduleEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidateScheduleResource candidateScheduleResource = new CandidateScheduleResource(candidateScheduleRepository);
        this.restCandidateScheduleEntityMockMvc = MockMvcBuilders.standaloneSetup(candidateScheduleResource)
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
    public static CandidateScheduleEntity createEntity(EntityManager em) {
        CandidateScheduleEntity candidateScheduleEntity = new CandidateScheduleEntity()
            .owner(DEFAULT_OWNER)
            .scheduleDate(DEFAULT_SCHEDULE_DATE);
        return candidateScheduleEntity;
    }

    @Before
    public void initTest() {
        candidateScheduleEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateScheduleEntity() throws Exception {
        int databaseSizeBeforeCreate = candidateScheduleRepository.findAll().size();

        // Create the CandidateScheduleEntity
        restCandidateScheduleEntityMockMvc.perform(post("/api/candidate-schedule-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateScheduleEntity)))
            .andExpect(status().isCreated());

        // Validate the CandidateScheduleEntity in the database
        List<CandidateScheduleEntity> candidateScheduleEntityList = candidateScheduleRepository.findAll();
        assertThat(candidateScheduleEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateScheduleEntity testCandidateScheduleEntity = candidateScheduleEntityList.get(candidateScheduleEntityList.size() - 1);
        assertThat(testCandidateScheduleEntity.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testCandidateScheduleEntity.getScheduleDate()).isEqualTo(DEFAULT_SCHEDULE_DATE);
    }

    @Test
    @Transactional
    public void createCandidateScheduleEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateScheduleRepository.findAll().size();

        // Create the CandidateScheduleEntity with an existing ID
        candidateScheduleEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateScheduleEntityMockMvc.perform(post("/api/candidate-schedule-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateScheduleEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateScheduleEntity in the database
        List<CandidateScheduleEntity> candidateScheduleEntityList = candidateScheduleRepository.findAll();
        assertThat(candidateScheduleEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandidateScheduleEntities() throws Exception {
        // Initialize the database
        candidateScheduleRepository.saveAndFlush(candidateScheduleEntity);

        // Get all the candidateScheduleEntityList
        restCandidateScheduleEntityMockMvc.perform(get("/api/candidate-schedule-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateScheduleEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.intValue())))
            .andExpect(jsonPath("$.[*].scheduleDate").value(hasItem(sameInstant(DEFAULT_SCHEDULE_DATE))));
    }

    @Test
    @Transactional
    public void getCandidateScheduleEntity() throws Exception {
        // Initialize the database
        candidateScheduleRepository.saveAndFlush(candidateScheduleEntity);

        // Get the candidateScheduleEntity
        restCandidateScheduleEntityMockMvc.perform(get("/api/candidate-schedule-entities/{id}", candidateScheduleEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateScheduleEntity.getId().intValue()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.intValue()))
            .andExpect(jsonPath("$.scheduleDate").value(sameInstant(DEFAULT_SCHEDULE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateScheduleEntity() throws Exception {
        // Get the candidateScheduleEntity
        restCandidateScheduleEntityMockMvc.perform(get("/api/candidate-schedule-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateScheduleEntity() throws Exception {
        // Initialize the database
        candidateScheduleRepository.saveAndFlush(candidateScheduleEntity);
        int databaseSizeBeforeUpdate = candidateScheduleRepository.findAll().size();

        // Update the candidateScheduleEntity
        CandidateScheduleEntity updatedCandidateScheduleEntity = candidateScheduleRepository.findOne(candidateScheduleEntity.getId());
        // Disconnect from session so that the updates on updatedCandidateScheduleEntity are not directly saved in db
        em.detach(updatedCandidateScheduleEntity);
        updatedCandidateScheduleEntity
            .owner(UPDATED_OWNER)
            .scheduleDate(UPDATED_SCHEDULE_DATE);

        restCandidateScheduleEntityMockMvc.perform(put("/api/candidate-schedule-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidateScheduleEntity)))
            .andExpect(status().isOk());

        // Validate the CandidateScheduleEntity in the database
        List<CandidateScheduleEntity> candidateScheduleEntityList = candidateScheduleRepository.findAll();
        assertThat(candidateScheduleEntityList).hasSize(databaseSizeBeforeUpdate);
        CandidateScheduleEntity testCandidateScheduleEntity = candidateScheduleEntityList.get(candidateScheduleEntityList.size() - 1);
        assertThat(testCandidateScheduleEntity.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testCandidateScheduleEntity.getScheduleDate()).isEqualTo(UPDATED_SCHEDULE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidateScheduleEntity() throws Exception {
        int databaseSizeBeforeUpdate = candidateScheduleRepository.findAll().size();

        // Create the CandidateScheduleEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidateScheduleEntityMockMvc.perform(put("/api/candidate-schedule-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateScheduleEntity)))
            .andExpect(status().isCreated());

        // Validate the CandidateScheduleEntity in the database
        List<CandidateScheduleEntity> candidateScheduleEntityList = candidateScheduleRepository.findAll();
        assertThat(candidateScheduleEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidateScheduleEntity() throws Exception {
        // Initialize the database
        candidateScheduleRepository.saveAndFlush(candidateScheduleEntity);
        int databaseSizeBeforeDelete = candidateScheduleRepository.findAll().size();

        // Get the candidateScheduleEntity
        restCandidateScheduleEntityMockMvc.perform(delete("/api/candidate-schedule-entities/{id}", candidateScheduleEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateScheduleEntity> candidateScheduleEntityList = candidateScheduleRepository.findAll();
        assertThat(candidateScheduleEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateScheduleEntity.class);
        CandidateScheduleEntity candidateScheduleEntity1 = new CandidateScheduleEntity();
        candidateScheduleEntity1.setId(1L);
        CandidateScheduleEntity candidateScheduleEntity2 = new CandidateScheduleEntity();
        candidateScheduleEntity2.setId(candidateScheduleEntity1.getId());
        assertThat(candidateScheduleEntity1).isEqualTo(candidateScheduleEntity2);
        candidateScheduleEntity2.setId(2L);
        assertThat(candidateScheduleEntity1).isNotEqualTo(candidateScheduleEntity2);
        candidateScheduleEntity1.setId(null);
        assertThat(candidateScheduleEntity1).isNotEqualTo(candidateScheduleEntity2);
    }
}
