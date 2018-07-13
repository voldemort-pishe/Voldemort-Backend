package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.entity.jpa.CompanyPipelineEntity;
import io.avand.repository.jpa.CompanyPipelineRepository;
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
 * Test class for the CompanyPipelineEntityResource REST controller.
 *
 * @see CompanyPipelineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class CompanyPipelineResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    @Autowired
    private CompanyPipelineRepository companyPipelineRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyPipelineEntityMockMvc;

    private CompanyPipelineEntity companyPipelineEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyPipelineResource companyPipelineResource = new CompanyPipelineResource(companyPipelineRepository);
        this.restCompanyPipelineEntityMockMvc = MockMvcBuilders.standaloneSetup(companyPipelineResource)
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
    public static CompanyPipelineEntity createEntity(EntityManager em) {
        CompanyPipelineEntity companyPipelineEntity = new CompanyPipelineEntity()
            .title(DEFAULT_TITLE)
            .weight(DEFAULT_WEIGHT);
        return companyPipelineEntity;
    }

    @Before
    public void initTest() {
        companyPipelineEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyPipelineEntity() throws Exception {
        int databaseSizeBeforeCreate = companyPipelineRepository.findAll().size();

        // Create the CompanyPipelineEntity
        restCompanyPipelineEntityMockMvc.perform(post("/api/company-pipeline-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyPipelineEntity)))
            .andExpect(status().isCreated());

        // Validate the CompanyPipelineEntity in the database
        List<CompanyPipelineEntity> companyPipelineEntityList = companyPipelineRepository.findAll();
        assertThat(companyPipelineEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyPipelineEntity testCompanyPipelineEntity = companyPipelineEntityList.get(companyPipelineEntityList.size() - 1);
        assertThat(testCompanyPipelineEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCompanyPipelineEntity.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createCompanyPipelineEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyPipelineRepository.findAll().size();

        // Create the CompanyPipelineEntity with an existing ID
        companyPipelineEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyPipelineEntityMockMvc.perform(post("/api/company-pipeline-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyPipelineEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyPipelineEntity in the database
        List<CompanyPipelineEntity> companyPipelineEntityList = companyPipelineRepository.findAll();
        assertThat(companyPipelineEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyPipelineEntities() throws Exception {
        // Initialize the database
        companyPipelineRepository.saveAndFlush(companyPipelineEntity);

        // Get all the companyPipelineEntityList
        restCompanyPipelineEntityMockMvc.perform(get("/api/company-pipeline-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyPipelineEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }

    @Test
    @Transactional
    public void getCompanyPipelineEntity() throws Exception {
        // Initialize the database
        companyPipelineRepository.saveAndFlush(companyPipelineEntity);

        // Get the companyPipelineEntity
        restCompanyPipelineEntityMockMvc.perform(get("/api/company-pipeline-entities/{id}", companyPipelineEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyPipelineEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyPipelineEntity() throws Exception {
        // Get the companyPipelineEntity
        restCompanyPipelineEntityMockMvc.perform(get("/api/company-pipeline-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyPipelineEntity() throws Exception {
        // Initialize the database
        companyPipelineRepository.saveAndFlush(companyPipelineEntity);
        int databaseSizeBeforeUpdate = companyPipelineRepository.findAll().size();

        // Update the companyPipelineEntity
        CompanyPipelineEntity updatedCompanyPipelineEntity = companyPipelineRepository.findOne(companyPipelineEntity.getId());
        // Disconnect from session so that the updates on updatedCompanyPipelineEntity are not directly saved in db
        em.detach(updatedCompanyPipelineEntity);
        updatedCompanyPipelineEntity
            .title(UPDATED_TITLE)
            .weight(UPDATED_WEIGHT);

        restCompanyPipelineEntityMockMvc.perform(put("/api/company-pipeline-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyPipelineEntity)))
            .andExpect(status().isOk());

        // Validate the CompanyPipelineEntity in the database
        List<CompanyPipelineEntity> companyPipelineEntityList = companyPipelineRepository.findAll();
        assertThat(companyPipelineEntityList).hasSize(databaseSizeBeforeUpdate);
        CompanyPipelineEntity testCompanyPipelineEntity = companyPipelineEntityList.get(companyPipelineEntityList.size() - 1);
        assertThat(testCompanyPipelineEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCompanyPipelineEntity.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyPipelineEntity() throws Exception {
        int databaseSizeBeforeUpdate = companyPipelineRepository.findAll().size();

        // Create the CompanyPipelineEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyPipelineEntityMockMvc.perform(put("/api/company-pipeline-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyPipelineEntity)))
            .andExpect(status().isCreated());

        // Validate the CompanyPipelineEntity in the database
        List<CompanyPipelineEntity> companyPipelineEntityList = companyPipelineRepository.findAll();
        assertThat(companyPipelineEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyPipelineEntity() throws Exception {
        // Initialize the database
        companyPipelineRepository.saveAndFlush(companyPipelineEntity);
        int databaseSizeBeforeDelete = companyPipelineRepository.findAll().size();

        // Get the companyPipelineEntity
        restCompanyPipelineEntityMockMvc.perform(delete("/api/company-pipeline-entities/{id}", companyPipelineEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyPipelineEntity> companyPipelineEntityList = companyPipelineRepository.findAll();
        assertThat(companyPipelineEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyPipelineEntity.class);
        CompanyPipelineEntity companyPipelineEntity1 = new CompanyPipelineEntity();
        companyPipelineEntity1.setId(1L);
        CompanyPipelineEntity companyPipelineEntity2 = new CompanyPipelineEntity();
        companyPipelineEntity2.setId(companyPipelineEntity1.getId());
        assertThat(companyPipelineEntity1).isEqualTo(companyPipelineEntity2);
        companyPipelineEntity2.setId(2L);
        assertThat(companyPipelineEntity1).isNotEqualTo(companyPipelineEntity2);
        companyPipelineEntity1.setId(null);
        assertThat(companyPipelineEntity1).isNotEqualTo(companyPipelineEntity2);
    }
}
