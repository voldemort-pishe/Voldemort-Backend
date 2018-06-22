package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.EvaluationCriteriaEntity;
import io.avand.repository.EvaluationCriteriaRepository;
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

/**
 * Test class for the EvaluationCriteriaEntityResource REST controller.
 *
 * @see EvaluationCriteriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class EvaluationCriteriaResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EvaluationCriteriaRepository evaluationCriteriaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEvaluationCriteriaEntityMockMvc;

    private EvaluationCriteriaEntity evaluationCriteriaEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvaluationCriteriaResource evaluationCriteriaResource = new EvaluationCriteriaResource(evaluationCriteriaRepository);
        this.restEvaluationCriteriaEntityMockMvc = MockMvcBuilders.standaloneSetup(evaluationCriteriaResource)
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
    public static EvaluationCriteriaEntity createEntity(EntityManager em) {
        EvaluationCriteriaEntity evaluationCriteriaEntity = new EvaluationCriteriaEntity()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return evaluationCriteriaEntity;
    }

    @Before
    public void initTest() {
        evaluationCriteriaEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluationCriteriaEntity() throws Exception {
        int databaseSizeBeforeCreate = evaluationCriteriaRepository.findAll().size();

        // Create the EvaluationCriteriaEntity
        restEvaluationCriteriaEntityMockMvc.perform(post("/api/evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationCriteriaEntity)))
            .andExpect(status().isCreated());

        // Validate the EvaluationCriteriaEntity in the database
        List<EvaluationCriteriaEntity> evaluationCriteriaEntityList = evaluationCriteriaRepository.findAll();
        assertThat(evaluationCriteriaEntityList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationCriteriaEntity testEvaluationCriteriaEntity = evaluationCriteriaEntityList.get(evaluationCriteriaEntityList.size() - 1);
        assertThat(testEvaluationCriteriaEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvaluationCriteriaEntity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEvaluationCriteriaEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluationCriteriaRepository.findAll().size();

        // Create the EvaluationCriteriaEntity with an existing ID
        evaluationCriteriaEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationCriteriaEntityMockMvc.perform(post("/api/evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationCriteriaEntity)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCriteriaEntity in the database
        List<EvaluationCriteriaEntity> evaluationCriteriaEntityList = evaluationCriteriaRepository.findAll();
        assertThat(evaluationCriteriaEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvaluationCriteriaEntities() throws Exception {
        // Initialize the database
        evaluationCriteriaRepository.saveAndFlush(evaluationCriteriaEntity);

        // Get all the evaluationCriteriaEntityList
        restEvaluationCriteriaEntityMockMvc.perform(get("/api/evaluation-criteria-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationCriteriaEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEvaluationCriteriaEntity() throws Exception {
        // Initialize the database
        evaluationCriteriaRepository.saveAndFlush(evaluationCriteriaEntity);

        // Get the evaluationCriteriaEntity
        restEvaluationCriteriaEntityMockMvc.perform(get("/api/evaluation-criteria-entities/{id}", evaluationCriteriaEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationCriteriaEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluationCriteriaEntity() throws Exception {
        // Get the evaluationCriteriaEntity
        restEvaluationCriteriaEntityMockMvc.perform(get("/api/evaluation-criteria-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluationCriteriaEntity() throws Exception {
        // Initialize the database
        evaluationCriteriaRepository.saveAndFlush(evaluationCriteriaEntity);
        int databaseSizeBeforeUpdate = evaluationCriteriaRepository.findAll().size();

        // Update the evaluationCriteriaEntity
        EvaluationCriteriaEntity updatedEvaluationCriteriaEntity = evaluationCriteriaRepository.findOne(evaluationCriteriaEntity.getId());
        // Disconnect from session so that the updates on updatedEvaluationCriteriaEntity are not directly saved in db
        em.detach(updatedEvaluationCriteriaEntity);
        updatedEvaluationCriteriaEntity
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);

        restEvaluationCriteriaEntityMockMvc.perform(put("/api/evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvaluationCriteriaEntity)))
            .andExpect(status().isOk());

        // Validate the EvaluationCriteriaEntity in the database
        List<EvaluationCriteriaEntity> evaluationCriteriaEntityList = evaluationCriteriaRepository.findAll();
        assertThat(evaluationCriteriaEntityList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCriteriaEntity testEvaluationCriteriaEntity = evaluationCriteriaEntityList.get(evaluationCriteriaEntityList.size() - 1);
        assertThat(testEvaluationCriteriaEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvaluationCriteriaEntity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluationCriteriaEntity() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCriteriaRepository.findAll().size();

        // Create the EvaluationCriteriaEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEvaluationCriteriaEntityMockMvc.perform(put("/api/evaluation-criteria-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationCriteriaEntity)))
            .andExpect(status().isCreated());

        // Validate the EvaluationCriteriaEntity in the database
        List<EvaluationCriteriaEntity> evaluationCriteriaEntityList = evaluationCriteriaRepository.findAll();
        assertThat(evaluationCriteriaEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEvaluationCriteriaEntity() throws Exception {
        // Initialize the database
        evaluationCriteriaRepository.saveAndFlush(evaluationCriteriaEntity);
        int databaseSizeBeforeDelete = evaluationCriteriaRepository.findAll().size();

        // Get the evaluationCriteriaEntity
        restEvaluationCriteriaEntityMockMvc.perform(delete("/api/evaluation-criteria-entities/{id}", evaluationCriteriaEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EvaluationCriteriaEntity> evaluationCriteriaEntityList = evaluationCriteriaRepository.findAll();
        assertThat(evaluationCriteriaEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationCriteriaEntity.class);
        EvaluationCriteriaEntity evaluationCriteriaEntity1 = new EvaluationCriteriaEntity();
        evaluationCriteriaEntity1.setId(1L);
        EvaluationCriteriaEntity evaluationCriteriaEntity2 = new EvaluationCriteriaEntity();
        evaluationCriteriaEntity2.setId(evaluationCriteriaEntity1.getId());
        assertThat(evaluationCriteriaEntity1).isEqualTo(evaluationCriteriaEntity2);
        evaluationCriteriaEntity2.setId(2L);
        assertThat(evaluationCriteriaEntity1).isNotEqualTo(evaluationCriteriaEntity2);
        evaluationCriteriaEntity1.setId(null);
        assertThat(evaluationCriteriaEntity1).isNotEqualTo(evaluationCriteriaEntity2);
    }
}
