package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.CandidateEvaluationCriteriaEntity;
import io.avand.repository.CandidateEvaluationCriteriaEntityRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static io.avand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CandidateEvaluationCriteriaEntityResource REST controller.
 *
 * @see CandidateEvaluationCriteriaEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class CandidateEvaluationCriteriaEntityResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_USER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_COMMENT = "BBBBBBBBBB";

    private static final Long DEFAULT_EVALUATION_CRITERIA_ID = 1L;
    private static final Long UPDATED_EVALUATION_CRITERIA_ID = 2L;

    @Autowired
    private CandidateEvaluationCriteriaEntityRepository candidateEvaluationCriteriaEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidateEvaluationCriteriaEntityMockMvc;

    private CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidateEvaluationCriteriaEntityResource candidateEvaluationCriteriaEntityResource = new CandidateEvaluationCriteriaEntityResource(candidateEvaluationCriteriaEntityRepository);
        this.restCandidateEvaluationCriteriaEntityMockMvc = MockMvcBuilders.standaloneSetup(candidateEvaluationCriteriaEntityResource)
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
    public static CandidateEvaluationCriteriaEntity createEntity(EntityManager em) {
        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity = new CandidateEvaluationCriteriaEntity()
            .userId(DEFAULT_USER_ID)
            .userComment(DEFAULT_USER_COMMENT)
            .evaluationCriteriaId(DEFAULT_EVALUATION_CRITERIA_ID);
        return candidateEvaluationCriteriaEntity;
    }

    @Before
    public void initTest() {
        candidateEvaluationCriteriaEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateEvaluationCriteriaEntity() throws Exception {
        int databaseSizeBeforeCreate = candidateEvaluationCriteriaEntityRepository.findAll().size();

        // Create the CandidateEvaluationCriteriaEntity
        restCandidateEvaluationCriteriaEntityMockMvc.perform(post("/api/candidate-evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateEvaluationCriteriaEntity)))
            .andExpect(status().isCreated());

        // Validate the CandidateEvaluationCriteriaEntity in the database
        List<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteriaEntityList = candidateEvaluationCriteriaEntityRepository.findAll();
        assertThat(candidateEvaluationCriteriaEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateEvaluationCriteriaEntity testCandidateEvaluationCriteriaEntity = candidateEvaluationCriteriaEntityList.get(candidateEvaluationCriteriaEntityList.size() - 1);
        assertThat(testCandidateEvaluationCriteriaEntity.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCandidateEvaluationCriteriaEntity.getUserComment()).isEqualTo(DEFAULT_USER_COMMENT);
        assertThat(testCandidateEvaluationCriteriaEntity.getEvaluationCriteriaId()).isEqualTo(DEFAULT_EVALUATION_CRITERIA_ID);
    }

    @Test
    @Transactional
    public void createCandidateEvaluationCriteriaEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateEvaluationCriteriaEntityRepository.findAll().size();

        // Create the CandidateEvaluationCriteriaEntity with an existing ID
        candidateEvaluationCriteriaEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateEvaluationCriteriaEntityMockMvc.perform(post("/api/candidate-evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateEvaluationCriteriaEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateEvaluationCriteriaEntity in the database
        List<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteriaEntityList = candidateEvaluationCriteriaEntityRepository.findAll();
        assertThat(candidateEvaluationCriteriaEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandidateEvaluationCriteriaEntities() throws Exception {
        // Initialize the database
        candidateEvaluationCriteriaEntityRepository.saveAndFlush(candidateEvaluationCriteriaEntity);

        // Get all the candidateEvaluationCriteriaEntityList
        restCandidateEvaluationCriteriaEntityMockMvc.perform(get("/api/candidate-evaluation-criteria-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateEvaluationCriteriaEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userComment").value(hasItem(DEFAULT_USER_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].evaluationCriteriaId").value(hasItem(DEFAULT_EVALUATION_CRITERIA_ID.intValue())));
    }

    @Test
    @Transactional
    public void getCandidateEvaluationCriteriaEntity() throws Exception {
        // Initialize the database
        candidateEvaluationCriteriaEntityRepository.saveAndFlush(candidateEvaluationCriteriaEntity);

        // Get the candidateEvaluationCriteriaEntity
        restCandidateEvaluationCriteriaEntityMockMvc.perform(get("/api/candidate-evaluation-criteria-entities/{id}", candidateEvaluationCriteriaEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateEvaluationCriteriaEntity.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userComment").value(DEFAULT_USER_COMMENT.toString()))
            .andExpect(jsonPath("$.evaluationCriteriaId").value(DEFAULT_EVALUATION_CRITERIA_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateEvaluationCriteriaEntity() throws Exception {
        // Get the candidateEvaluationCriteriaEntity
        restCandidateEvaluationCriteriaEntityMockMvc.perform(get("/api/candidate-evaluation-criteria-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateEvaluationCriteriaEntity() throws Exception {
        // Initialize the database
        candidateEvaluationCriteriaEntityRepository.saveAndFlush(candidateEvaluationCriteriaEntity);
        int databaseSizeBeforeUpdate = candidateEvaluationCriteriaEntityRepository.findAll().size();

        // Update the candidateEvaluationCriteriaEntity
        CandidateEvaluationCriteriaEntity updatedCandidateEvaluationCriteriaEntity = candidateEvaluationCriteriaEntityRepository.findOne(candidateEvaluationCriteriaEntity.getId());
        // Disconnect from session so that the updates on updatedCandidateEvaluationCriteriaEntity are not directly saved in db
        em.detach(updatedCandidateEvaluationCriteriaEntity);
        updatedCandidateEvaluationCriteriaEntity
            .userId(UPDATED_USER_ID)
            .userComment(UPDATED_USER_COMMENT)
            .evaluationCriteriaId(UPDATED_EVALUATION_CRITERIA_ID);

        restCandidateEvaluationCriteriaEntityMockMvc.perform(put("/api/candidate-evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidateEvaluationCriteriaEntity)))
            .andExpect(status().isOk());

        // Validate the CandidateEvaluationCriteriaEntity in the database
        List<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteriaEntityList = candidateEvaluationCriteriaEntityRepository.findAll();
        assertThat(candidateEvaluationCriteriaEntityList).hasSize(databaseSizeBeforeUpdate);
        CandidateEvaluationCriteriaEntity testCandidateEvaluationCriteriaEntity = candidateEvaluationCriteriaEntityList.get(candidateEvaluationCriteriaEntityList.size() - 1);
        assertThat(testCandidateEvaluationCriteriaEntity.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCandidateEvaluationCriteriaEntity.getUserComment()).isEqualTo(UPDATED_USER_COMMENT);
        assertThat(testCandidateEvaluationCriteriaEntity.getEvaluationCriteriaId()).isEqualTo(UPDATED_EVALUATION_CRITERIA_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidateEvaluationCriteriaEntity() throws Exception {
        int databaseSizeBeforeUpdate = candidateEvaluationCriteriaEntityRepository.findAll().size();

        // Create the CandidateEvaluationCriteriaEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidateEvaluationCriteriaEntityMockMvc.perform(put("/api/candidate-evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateEvaluationCriteriaEntity)))
            .andExpect(status().isCreated());

        // Validate the CandidateEvaluationCriteriaEntity in the database
        List<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteriaEntityList = candidateEvaluationCriteriaEntityRepository.findAll();
        assertThat(candidateEvaluationCriteriaEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidateEvaluationCriteriaEntity() throws Exception {
        // Initialize the database
        candidateEvaluationCriteriaEntityRepository.saveAndFlush(candidateEvaluationCriteriaEntity);
        int databaseSizeBeforeDelete = candidateEvaluationCriteriaEntityRepository.findAll().size();

        // Get the candidateEvaluationCriteriaEntity
        restCandidateEvaluationCriteriaEntityMockMvc.perform(delete("/api/candidate-evaluation-criteria-entities/{id}", candidateEvaluationCriteriaEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteriaEntityList = candidateEvaluationCriteriaEntityRepository.findAll();
        assertThat(candidateEvaluationCriteriaEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateEvaluationCriteriaEntity.class);
        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity1 = new CandidateEvaluationCriteriaEntity();
        candidateEvaluationCriteriaEntity1.setId(1L);
        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity2 = new CandidateEvaluationCriteriaEntity();
        candidateEvaluationCriteriaEntity2.setId(candidateEvaluationCriteriaEntity1.getId());
        assertThat(candidateEvaluationCriteriaEntity1).isEqualTo(candidateEvaluationCriteriaEntity2);
        candidateEvaluationCriteriaEntity2.setId(2L);
        assertThat(candidateEvaluationCriteriaEntity1).isNotEqualTo(candidateEvaluationCriteriaEntity2);
        candidateEvaluationCriteriaEntity1.setId(null);
        assertThat(candidateEvaluationCriteriaEntity1).isNotEqualTo(candidateEvaluationCriteriaEntity2);
    }
}
