package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.PlanEntity;
import io.avand.repository.PlanEntityRepository;
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
 * Test class for the PlanEntityResource REST controller.
 *
 * @see PlanEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class PlanEntityResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    @Autowired
    private PlanEntityRepository planEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanEntityMockMvc;

    private PlanEntity planEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanEntityResource planEntityResource = new PlanEntityResource(planEntityRepository);
        this.restPlanEntityMockMvc = MockMvcBuilders.standaloneSetup(planEntityResource)
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
    public static PlanEntity createEntity(EntityManager em) {
        PlanEntity planEntity = new PlanEntity()
            .title(DEFAULT_TITLE)
            .amount(DEFAULT_AMOUNT);
        return planEntity;
    }

    @Before
    public void initTest() {
        planEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanEntity() throws Exception {
        int databaseSizeBeforeCreate = planEntityRepository.findAll().size();

        // Create the PlanEntity
        restPlanEntityMockMvc.perform(post("/api/plan-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planEntity)))
            .andExpect(status().isCreated());

        // Validate the PlanEntity in the database
        List<PlanEntity> planEntityList = planEntityRepository.findAll();
        assertThat(planEntityList).hasSize(databaseSizeBeforeCreate + 1);
        PlanEntity testPlanEntity = planEntityList.get(planEntityList.size() - 1);
        assertThat(testPlanEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPlanEntity.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createPlanEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planEntityRepository.findAll().size();

        // Create the PlanEntity with an existing ID
        planEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanEntityMockMvc.perform(post("/api/plan-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planEntity)))
            .andExpect(status().isBadRequest());

        // Validate the PlanEntity in the database
        List<PlanEntity> planEntityList = planEntityRepository.findAll();
        assertThat(planEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlanEntities() throws Exception {
        // Initialize the database
        planEntityRepository.saveAndFlush(planEntity);

        // Get all the planEntityList
        restPlanEntityMockMvc.perform(get("/api/plan-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)));
    }

    @Test
    @Transactional
    public void getPlanEntity() throws Exception {
        // Initialize the database
        planEntityRepository.saveAndFlush(planEntity);

        // Get the planEntity
        restPlanEntityMockMvc.perform(get("/api/plan-entities/{id}", planEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPlanEntity() throws Exception {
        // Get the planEntity
        restPlanEntityMockMvc.perform(get("/api/plan-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanEntity() throws Exception {
        // Initialize the database
        planEntityRepository.saveAndFlush(planEntity);
        int databaseSizeBeforeUpdate = planEntityRepository.findAll().size();

        // Update the planEntity
        PlanEntity updatedPlanEntity = planEntityRepository.findOne(planEntity.getId());
        // Disconnect from session so that the updates on updatedPlanEntity are not directly saved in db
        em.detach(updatedPlanEntity);
        updatedPlanEntity
            .title(UPDATED_TITLE)
            .amount(UPDATED_AMOUNT);

        restPlanEntityMockMvc.perform(put("/api/plan-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanEntity)))
            .andExpect(status().isOk());

        // Validate the PlanEntity in the database
        List<PlanEntity> planEntityList = planEntityRepository.findAll();
        assertThat(planEntityList).hasSize(databaseSizeBeforeUpdate);
        PlanEntity testPlanEntity = planEntityList.get(planEntityList.size() - 1);
        assertThat(testPlanEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPlanEntity.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanEntity() throws Exception {
        int databaseSizeBeforeUpdate = planEntityRepository.findAll().size();

        // Create the PlanEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanEntityMockMvc.perform(put("/api/plan-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planEntity)))
            .andExpect(status().isCreated());

        // Validate the PlanEntity in the database
        List<PlanEntity> planEntityList = planEntityRepository.findAll();
        assertThat(planEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlanEntity() throws Exception {
        // Initialize the database
        planEntityRepository.saveAndFlush(planEntity);
        int databaseSizeBeforeDelete = planEntityRepository.findAll().size();

        // Get the planEntity
        restPlanEntityMockMvc.perform(delete("/api/plan-entities/{id}", planEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanEntity> planEntityList = planEntityRepository.findAll();
        assertThat(planEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanEntity.class);
        PlanEntity planEntity1 = new PlanEntity();
        planEntity1.setId(1L);
        PlanEntity planEntity2 = new PlanEntity();
        planEntity2.setId(planEntity1.getId());
        assertThat(planEntity1).isEqualTo(planEntity2);
        planEntity2.setId(2L);
        assertThat(planEntity1).isNotEqualTo(planEntity2);
        planEntity1.setId(null);
        assertThat(planEntity1).isNotEqualTo(planEntity2);
    }
}
