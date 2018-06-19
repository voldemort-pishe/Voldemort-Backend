package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.AuthorityEntity;
import io.avand.repository.AuthorityEntityRepository;
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
 * Test class for the AuthorityEntityResource REST controller.
 *
 * @see AuthorityEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class AuthorityEntityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuthorityEntityRepository authorityEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuthorityEntityMockMvc;

    private AuthorityEntity authorityEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuthorityEntityResource authorityEntityResource = new AuthorityEntityResource(authorityEntityRepository);
        this.restAuthorityEntityMockMvc = MockMvcBuilders.standaloneSetup(authorityEntityResource)
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
    public static AuthorityEntity createEntity(EntityManager em) {
        AuthorityEntity authorityEntity = new AuthorityEntity()
            .name(DEFAULT_NAME);
        return authorityEntity;
    }

    @Before
    public void initTest() {
        authorityEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuthorityEntity() throws Exception {
        int databaseSizeBeforeCreate = authorityEntityRepository.findAll().size();

        // Create the AuthorityEntity
        restAuthorityEntityMockMvc.perform(post("/api/authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorityEntity)))
            .andExpect(status().isCreated());

        // Validate the AuthorityEntity in the database
        List<AuthorityEntity> authorityEntityList = authorityEntityRepository.findAll();
        assertThat(authorityEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AuthorityEntity testAuthorityEntity = authorityEntityList.get(authorityEntityList.size() - 1);
        assertThat(testAuthorityEntity.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAuthorityEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authorityEntityRepository.findAll().size();

        // Create the AuthorityEntity with an existing ID
        authorityEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorityEntityMockMvc.perform(post("/api/authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorityEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AuthorityEntity in the database
        List<AuthorityEntity> authorityEntityList = authorityEntityRepository.findAll();
        assertThat(authorityEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuthorityEntities() throws Exception {
        // Initialize the database
        authorityEntityRepository.saveAndFlush(authorityEntity);

        // Get all the authorityEntityList
        restAuthorityEntityMockMvc.perform(get("/api/authority-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authorityEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuthorityEntity() throws Exception {
        // Initialize the database
        authorityEntityRepository.saveAndFlush(authorityEntity);

        // Get the authorityEntity
        restAuthorityEntityMockMvc.perform(get("/api/authority-entities/{id}", authorityEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(authorityEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuthorityEntity() throws Exception {
        // Get the authorityEntity
        restAuthorityEntityMockMvc.perform(get("/api/authority-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthorityEntity() throws Exception {
        // Initialize the database
        authorityEntityRepository.saveAndFlush(authorityEntity);
        int databaseSizeBeforeUpdate = authorityEntityRepository.findAll().size();

        // Update the authorityEntity
        AuthorityEntity updatedAuthorityEntity = authorityEntityRepository.findOne(authorityEntity.getId());
        // Disconnect from session so that the updates on updatedAuthorityEntity are not directly saved in db
        em.detach(updatedAuthorityEntity);
        updatedAuthorityEntity
            .name(UPDATED_NAME);

        restAuthorityEntityMockMvc.perform(put("/api/authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuthorityEntity)))
            .andExpect(status().isOk());

        // Validate the AuthorityEntity in the database
        List<AuthorityEntity> authorityEntityList = authorityEntityRepository.findAll();
        assertThat(authorityEntityList).hasSize(databaseSizeBeforeUpdate);
        AuthorityEntity testAuthorityEntity = authorityEntityList.get(authorityEntityList.size() - 1);
        assertThat(testAuthorityEntity.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAuthorityEntity() throws Exception {
        int databaseSizeBeforeUpdate = authorityEntityRepository.findAll().size();

        // Create the AuthorityEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuthorityEntityMockMvc.perform(put("/api/authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authorityEntity)))
            .andExpect(status().isCreated());

        // Validate the AuthorityEntity in the database
        List<AuthorityEntity> authorityEntityList = authorityEntityRepository.findAll();
        assertThat(authorityEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuthorityEntity() throws Exception {
        // Initialize the database
        authorityEntityRepository.saveAndFlush(authorityEntity);
        int databaseSizeBeforeDelete = authorityEntityRepository.findAll().size();

        // Get the authorityEntity
        restAuthorityEntityMockMvc.perform(delete("/api/authority-entities/{id}", authorityEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthorityEntity> authorityEntityList = authorityEntityRepository.findAll();
        assertThat(authorityEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorityEntity.class);
        AuthorityEntity authorityEntity1 = new AuthorityEntity();
        authorityEntity1.setId(1L);
        AuthorityEntity authorityEntity2 = new AuthorityEntity();
        authorityEntity2.setId(authorityEntity1.getId());
        assertThat(authorityEntity1).isEqualTo(authorityEntity2);
        authorityEntity2.setId(2L);
        assertThat(authorityEntity1).isNotEqualTo(authorityEntity2);
        authorityEntity1.setId(null);
        assertThat(authorityEntity1).isNotEqualTo(authorityEntity2);
    }
}
