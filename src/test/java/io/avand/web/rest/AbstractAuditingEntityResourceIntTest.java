package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.AbstractAuditingEntity;
import io.avand.repository.AbstractAuditingEntityRepository;
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
 * Test class for the AbstractAuditingEntityResource REST controller.
 *
 * @see AbstractAuditingEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class AbstractAuditingEntityResourceIntTest {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AbstractAuditingEntityRepository abstractAuditingEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAbstractAuditingEntityMockMvc;

    private AbstractAuditingEntity abstractAuditingEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbstractAuditingEntityResource abstractAuditingEntityResource = new AbstractAuditingEntityResource(abstractAuditingEntityRepository);
        this.restAbstractAuditingEntityMockMvc = MockMvcBuilders.standaloneSetup(abstractAuditingEntityResource)
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
    public static AbstractAuditingEntity createEntity(EntityManager em) {
        AbstractAuditingEntity abstractAuditingEntity = new AbstractAuditingEntity()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return abstractAuditingEntity;
    }

    @Before
    public void initTest() {
        abstractAuditingEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbstractAuditingEntity() throws Exception {
        int databaseSizeBeforeCreate = abstractAuditingEntityRepository.findAll().size();

        // Create the AbstractAuditingEntity
        restAbstractAuditingEntityMockMvc.perform(post("/api/abstract-auditing-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abstractAuditingEntity)))
            .andExpect(status().isCreated());

        // Validate the AbstractAuditingEntity in the database
        List<AbstractAuditingEntity> abstractAuditingEntityList = abstractAuditingEntityRepository.findAll();
        assertThat(abstractAuditingEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AbstractAuditingEntity testAbstractAuditingEntity = abstractAuditingEntityList.get(abstractAuditingEntityList.size() - 1);
        assertThat(testAbstractAuditingEntity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAbstractAuditingEntity.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAbstractAuditingEntity.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAbstractAuditingEntity.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createAbstractAuditingEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abstractAuditingEntityRepository.findAll().size();

        // Create the AbstractAuditingEntity with an existing ID
        abstractAuditingEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbstractAuditingEntityMockMvc.perform(post("/api/abstract-auditing-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abstractAuditingEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AbstractAuditingEntity in the database
        List<AbstractAuditingEntity> abstractAuditingEntityList = abstractAuditingEntityRepository.findAll();
        assertThat(abstractAuditingEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAbstractAuditingEntities() throws Exception {
        // Initialize the database
        abstractAuditingEntityRepository.saveAndFlush(abstractAuditingEntity);

        // Get all the abstractAuditingEntityList
        restAbstractAuditingEntityMockMvc.perform(get("/api/abstract-auditing-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abstractAuditingEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    public void getAbstractAuditingEntity() throws Exception {
        // Initialize the database
        abstractAuditingEntityRepository.saveAndFlush(abstractAuditingEntity);

        // Get the abstractAuditingEntity
        restAbstractAuditingEntityMockMvc.perform(get("/api/abstract-auditing-entities/{id}", abstractAuditingEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(abstractAuditingEntity.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAbstractAuditingEntity() throws Exception {
        // Get the abstractAuditingEntity
        restAbstractAuditingEntityMockMvc.perform(get("/api/abstract-auditing-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbstractAuditingEntity() throws Exception {
        // Initialize the database
        abstractAuditingEntityRepository.saveAndFlush(abstractAuditingEntity);
        int databaseSizeBeforeUpdate = abstractAuditingEntityRepository.findAll().size();

        // Update the abstractAuditingEntity
        AbstractAuditingEntity updatedAbstractAuditingEntity = abstractAuditingEntityRepository.findOne(abstractAuditingEntity.getId());
        // Disconnect from session so that the updates on updatedAbstractAuditingEntity are not directly saved in db
        em.detach(updatedAbstractAuditingEntity);
        updatedAbstractAuditingEntity
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restAbstractAuditingEntityMockMvc.perform(put("/api/abstract-auditing-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbstractAuditingEntity)))
            .andExpect(status().isOk());

        // Validate the AbstractAuditingEntity in the database
        List<AbstractAuditingEntity> abstractAuditingEntityList = abstractAuditingEntityRepository.findAll();
        assertThat(abstractAuditingEntityList).hasSize(databaseSizeBeforeUpdate);
        AbstractAuditingEntity testAbstractAuditingEntity = abstractAuditingEntityList.get(abstractAuditingEntityList.size() - 1);
        assertThat(testAbstractAuditingEntity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAbstractAuditingEntity.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAbstractAuditingEntity.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAbstractAuditingEntity.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAbstractAuditingEntity() throws Exception {
        int databaseSizeBeforeUpdate = abstractAuditingEntityRepository.findAll().size();

        // Create the AbstractAuditingEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAbstractAuditingEntityMockMvc.perform(put("/api/abstract-auditing-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abstractAuditingEntity)))
            .andExpect(status().isCreated());

        // Validate the AbstractAuditingEntity in the database
        List<AbstractAuditingEntity> abstractAuditingEntityList = abstractAuditingEntityRepository.findAll();
        assertThat(abstractAuditingEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAbstractAuditingEntity() throws Exception {
        // Initialize the database
        abstractAuditingEntityRepository.saveAndFlush(abstractAuditingEntity);
        int databaseSizeBeforeDelete = abstractAuditingEntityRepository.findAll().size();

        // Get the abstractAuditingEntity
        restAbstractAuditingEntityMockMvc.perform(delete("/api/abstract-auditing-entities/{id}", abstractAuditingEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AbstractAuditingEntity> abstractAuditingEntityList = abstractAuditingEntityRepository.findAll();
        assertThat(abstractAuditingEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbstractAuditingEntity.class);
        AbstractAuditingEntity abstractAuditingEntity1 = new AbstractAuditingEntity();
        abstractAuditingEntity1.setId(1L);
        AbstractAuditingEntity abstractAuditingEntity2 = new AbstractAuditingEntity();
        abstractAuditingEntity2.setId(abstractAuditingEntity1.getId());
        assertThat(abstractAuditingEntity1).isEqualTo(abstractAuditingEntity2);
        abstractAuditingEntity2.setId(2L);
        assertThat(abstractAuditingEntity1).isNotEqualTo(abstractAuditingEntity2);
        abstractAuditingEntity1.setId(null);
        assertThat(abstractAuditingEntity1).isNotEqualTo(abstractAuditingEntity2);
    }
}
