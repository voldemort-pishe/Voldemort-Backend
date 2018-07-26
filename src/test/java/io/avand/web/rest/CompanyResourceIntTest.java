package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.service.CompanyService;
import io.avand.service.dto.CompanyDTO;
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
 * Test class for the CompanyEntityResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class CompanyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyEntityMockMvc;

    private CompanyDTO companyEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyResource companyResource = new CompanyResource(companyService);
        this.restCompanyEntityMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
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
    public static CompanyDTO createEntity(EntityManager em) {
        CompanyDTO companyEntity = new CompanyDTO();
        companyEntity.setNameEn(DEFAULT_NAME);
        // Add required entity
        return companyEntity;
    }

    @Before
    public void initTest() {
        companyEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyEntity() throws Exception {
        int databaseSizeBeforeCreate = companyService.findAll().size();

        // Create the CompanyEntity
        restCompanyEntityMockMvc.perform(post("/api/company")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEntity)))
            .andExpect(status().isCreated());

        // Validate the CompanyEntity in the database
        List<CompanyDTO> companyEntityList = companyService.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDTO testCompanyEntity = companyEntityList.get(companyEntityList.size() - 1);
        assertThat(testCompanyEntity.getNameEn()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCompanyEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyService.findAll().size();

        // Create the CompanyEntity with an existing ID
        companyEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyEntityMockMvc.perform(post("/api/company")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyEntity in the database
        List<CompanyDTO> companyEntityList = companyService.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyEntities() throws Exception {

        // Get all the companyEntityList
        restCompanyEntityMockMvc.perform(get("/api/company?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCompanyEntity() throws Exception {

        // Get the companyEntity
        restCompanyEntityMockMvc.perform(get("/api/company/{id}", companyEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyEntity() throws Exception {
        // Get the companyEntity
        restCompanyEntityMockMvc.perform(get("/api/company/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyEntity() throws Exception {
        // Initialize the database
        companyService.save(companyEntity);
        int databaseSizeBeforeUpdate = companyService.findAll().size();

        // Update the companyEntity
        CompanyDTO updatedCompanyEntity = companyService.findById(companyEntity.getId());
        // Disconnect from session so that the updates on updatedCompanyEntity are not directly saved in db
        em.detach(updatedCompanyEntity);
        updatedCompanyEntity
            .setNameEn(UPDATED_NAME);

        restCompanyEntityMockMvc.perform(put("/api/company")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyEntity)))
            .andExpect(status().isOk());

        // Validate the CompanyEntity in the database
        List<CompanyDTO> companyEntityList = companyService.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeUpdate);
        CompanyDTO testCompanyEntity = companyEntityList.get(companyEntityList.size() - 1);
        assertThat(testCompanyEntity.getNameEn()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyEntity() throws Exception {
        int databaseSizeBeforeUpdate = companyService.findAll().size();

        // Create the CompanyEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyEntityMockMvc.perform(put("/api/company")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEntity)))
            .andExpect(status().isCreated());

        // Validate the CompanyEntity in the database
        List<CompanyDTO> companyEntityList = companyService.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyEntity() throws Exception {
        // Initialize the database
        companyService.save(companyEntity);
        int databaseSizeBeforeDelete = companyService.findAll().size();

        // Get the companyEntity
        restCompanyEntityMockMvc.perform(delete("/api/company/{id}", companyEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDTO> companyEntityList = companyService.findAll();
        assertThat(companyEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEntity.class);
        CompanyEntity companyEntity1 = new CompanyEntity();
        companyEntity1.setId(1L);
        CompanyEntity companyEntity2 = new CompanyEntity();
        companyEntity2.setId(companyEntity1.getId());
        assertThat(companyEntity1).isEqualTo(companyEntity2);
        companyEntity2.setId(2L);
        assertThat(companyEntity1).isNotEqualTo(companyEntity2);
        companyEntity1.setId(null);
        assertThat(companyEntity1).isNotEqualTo(companyEntity2);
    }
}
