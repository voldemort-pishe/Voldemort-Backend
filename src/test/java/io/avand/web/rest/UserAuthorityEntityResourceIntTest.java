package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.UserAuthorityEntity;
import io.avand.repository.UserAuthorityEntityRepository;
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
 * Test class for the UserAuthorityEntityResource REST controller.
 *
 * @see UserAuthorityEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class UserAuthorityEntityResourceIntTest {

    private static final String DEFAULT_AUTHORITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY_NAME = "BBBBBBBBBB";

    @Autowired
    private UserAuthorityEntityRepository userAuthorityEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserAuthorityEntityMockMvc;

    private UserAuthorityEntity userAuthorityEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAuthorityEntityResource userAuthorityEntityResource = new UserAuthorityEntityResource(userAuthorityEntityRepository);
        this.restUserAuthorityEntityMockMvc = MockMvcBuilders.standaloneSetup(userAuthorityEntityResource)
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
    public static UserAuthorityEntity createEntity(EntityManager em) {
        UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity()
            .authorityName(DEFAULT_AUTHORITY_NAME);
        return userAuthorityEntity;
    }

    @Before
    public void initTest() {
        userAuthorityEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAuthorityEntity() throws Exception {
        int databaseSizeBeforeCreate = userAuthorityEntityRepository.findAll().size();

        // Create the UserAuthorityEntity
        restUserAuthorityEntityMockMvc.perform(post("/api/user-authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorityEntity)))
            .andExpect(status().isCreated());

        // Validate the UserAuthorityEntity in the database
        List<UserAuthorityEntity> userAuthorityEntityList = userAuthorityEntityRepository.findAll();
        assertThat(userAuthorityEntityList).hasSize(databaseSizeBeforeCreate + 1);
        UserAuthorityEntity testUserAuthorityEntity = userAuthorityEntityList.get(userAuthorityEntityList.size() - 1);
        assertThat(testUserAuthorityEntity.getAuthorityName()).isEqualTo(DEFAULT_AUTHORITY_NAME);
    }

    @Test
    @Transactional
    public void createUserAuthorityEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAuthorityEntityRepository.findAll().size();

        // Create the UserAuthorityEntity with an existing ID
        userAuthorityEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAuthorityEntityMockMvc.perform(post("/api/user-authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorityEntity)))
            .andExpect(status().isBadRequest());

        // Validate the UserAuthorityEntity in the database
        List<UserAuthorityEntity> userAuthorityEntityList = userAuthorityEntityRepository.findAll();
        assertThat(userAuthorityEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAuthorityEntities() throws Exception {
        // Initialize the database
        userAuthorityEntityRepository.saveAndFlush(userAuthorityEntity);

        // Get all the userAuthorityEntityList
        restUserAuthorityEntityMockMvc.perform(get("/api/user-authority-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAuthorityEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].authorityName").value(hasItem(DEFAULT_AUTHORITY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getUserAuthorityEntity() throws Exception {
        // Initialize the database
        userAuthorityEntityRepository.saveAndFlush(userAuthorityEntity);

        // Get the userAuthorityEntity
        restUserAuthorityEntityMockMvc.perform(get("/api/user-authority-entities/{id}", userAuthorityEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAuthorityEntity.getId().intValue()))
            .andExpect(jsonPath("$.authorityName").value(DEFAULT_AUTHORITY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAuthorityEntity() throws Exception {
        // Get the userAuthorityEntity
        restUserAuthorityEntityMockMvc.perform(get("/api/user-authority-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAuthorityEntity() throws Exception {
        // Initialize the database
        userAuthorityEntityRepository.saveAndFlush(userAuthorityEntity);
        int databaseSizeBeforeUpdate = userAuthorityEntityRepository.findAll().size();

        // Update the userAuthorityEntity
        UserAuthorityEntity updatedUserAuthorityEntity = userAuthorityEntityRepository.findOne(userAuthorityEntity.getId());
        // Disconnect from session so that the updates on updatedUserAuthorityEntity are not directly saved in db
        em.detach(updatedUserAuthorityEntity);
        updatedUserAuthorityEntity
            .authorityName(UPDATED_AUTHORITY_NAME);

        restUserAuthorityEntityMockMvc.perform(put("/api/user-authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserAuthorityEntity)))
            .andExpect(status().isOk());

        // Validate the UserAuthorityEntity in the database
        List<UserAuthorityEntity> userAuthorityEntityList = userAuthorityEntityRepository.findAll();
        assertThat(userAuthorityEntityList).hasSize(databaseSizeBeforeUpdate);
        UserAuthorityEntity testUserAuthorityEntity = userAuthorityEntityList.get(userAuthorityEntityList.size() - 1);
        assertThat(testUserAuthorityEntity.getAuthorityName()).isEqualTo(UPDATED_AUTHORITY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAuthorityEntity() throws Exception {
        int databaseSizeBeforeUpdate = userAuthorityEntityRepository.findAll().size();

        // Create the UserAuthorityEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserAuthorityEntityMockMvc.perform(put("/api/user-authority-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAuthorityEntity)))
            .andExpect(status().isCreated());

        // Validate the UserAuthorityEntity in the database
        List<UserAuthorityEntity> userAuthorityEntityList = userAuthorityEntityRepository.findAll();
        assertThat(userAuthorityEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserAuthorityEntity() throws Exception {
        // Initialize the database
        userAuthorityEntityRepository.saveAndFlush(userAuthorityEntity);
        int databaseSizeBeforeDelete = userAuthorityEntityRepository.findAll().size();

        // Get the userAuthorityEntity
        restUserAuthorityEntityMockMvc.perform(delete("/api/user-authority-entities/{id}", userAuthorityEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAuthorityEntity> userAuthorityEntityList = userAuthorityEntityRepository.findAll();
        assertThat(userAuthorityEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAuthorityEntity.class);
        UserAuthorityEntity userAuthorityEntity1 = new UserAuthorityEntity();
        userAuthorityEntity1.setId(1L);
        UserAuthorityEntity userAuthorityEntity2 = new UserAuthorityEntity();
        userAuthorityEntity2.setId(userAuthorityEntity1.getId());
        assertThat(userAuthorityEntity1).isEqualTo(userAuthorityEntity2);
        userAuthorityEntity2.setId(2L);
        assertThat(userAuthorityEntity1).isNotEqualTo(userAuthorityEntity2);
        userAuthorityEntity1.setId(null);
        assertThat(userAuthorityEntity1).isNotEqualTo(userAuthorityEntity2);
    }
}
