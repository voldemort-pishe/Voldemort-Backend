package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.JobEntity;
import io.avand.domain.CandidateEntity;
import io.avand.domain.CompanyEntity;
import io.avand.repository.JobRepository;
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
 * Test class for the JobEntityResource REST controller.
 *
 * @see JobResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class JobResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobEntityMockMvc;

    private JobEntity jobEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobResource jobResource = new JobResource(jobRepository);
        this.restJobEntityMockMvc = MockMvcBuilders.standaloneSetup(jobResource)
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
    public static JobEntity createEntity(EntityManager em) {
        JobEntity jobEntity = new JobEntity()
            .name(DEFAULT_NAME);
        // Add required entity
        CandidateEntity candidate = CandidateResourceIntTest.createEntity(em);
        em.persist(candidate);
        em.flush();
        jobEntity.setCandidate(candidate);
        // Add required entity
        CompanyEntity company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        jobEntity.setCompany(company);
        return jobEntity;
    }

    @Before
    public void initTest() {
        jobEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobEntity() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the JobEntity
        restJobEntityMockMvc.perform(post("/api/job-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobEntity)))
            .andExpect(status().isCreated());

        // Validate the JobEntity in the database
        List<JobEntity> jobEntityList = jobRepository.findAll();
        assertThat(jobEntityList).hasSize(databaseSizeBeforeCreate + 1);
        JobEntity testJobEntity = jobEntityList.get(jobEntityList.size() - 1);
        assertThat(testJobEntity.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createJobEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the JobEntity with an existing ID
        jobEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobEntityMockMvc.perform(post("/api/job-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobEntity)))
            .andExpect(status().isBadRequest());

        // Validate the JobEntity in the database
        List<JobEntity> jobEntityList = jobRepository.findAll();
        assertThat(jobEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobEntities() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(jobEntity);

        // Get all the jobEntityList
        restJobEntityMockMvc.perform(get("/api/job-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getJobEntity() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(jobEntity);

        // Get the jobEntity
        restJobEntityMockMvc.perform(get("/api/job-entities/{id}", jobEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobEntity() throws Exception {
        // Get the jobEntity
        restJobEntityMockMvc.perform(get("/api/job-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobEntity() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(jobEntity);
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the jobEntity
        JobEntity updatedJobEntity = jobRepository.findOne(jobEntity.getId());
        // Disconnect from session so that the updates on updatedJobEntity are not directly saved in db
        em.detach(updatedJobEntity);
        updatedJobEntity
            .name(UPDATED_NAME);

        restJobEntityMockMvc.perform(put("/api/job-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobEntity)))
            .andExpect(status().isOk());

        // Validate the JobEntity in the database
        List<JobEntity> jobEntityList = jobRepository.findAll();
        assertThat(jobEntityList).hasSize(databaseSizeBeforeUpdate);
        JobEntity testJobEntity = jobEntityList.get(jobEntityList.size() - 1);
        assertThat(testJobEntity.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingJobEntity() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Create the JobEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobEntityMockMvc.perform(put("/api/job-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobEntity)))
            .andExpect(status().isCreated());

        // Validate the JobEntity in the database
        List<JobEntity> jobEntityList = jobRepository.findAll();
        assertThat(jobEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobEntity() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(jobEntity);
        int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Get the jobEntity
        restJobEntityMockMvc.perform(delete("/api/job-entities/{id}", jobEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobEntity> jobEntityList = jobRepository.findAll();
        assertThat(jobEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobEntity.class);
        JobEntity jobEntity1 = new JobEntity();
        jobEntity1.setId(1L);
        JobEntity jobEntity2 = new JobEntity();
        jobEntity2.setId(jobEntity1.getId());
        assertThat(jobEntity1).isEqualTo(jobEntity2);
        jobEntity2.setId(2L);
        assertThat(jobEntity1).isNotEqualTo(jobEntity2);
        jobEntity1.setId(null);
        assertThat(jobEntity1).isNotEqualTo(jobEntity2);
    }
}
