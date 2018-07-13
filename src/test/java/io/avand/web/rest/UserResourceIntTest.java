package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.entity.jpa.UserEntity;
import io.avand.repository.jpa.UserRepository;
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
 * Test class for the UserEntityResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class UserResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESET_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESET_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserEntityMockMvc;

    private UserEntity userEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserResource userResource = new UserResource(userRepository);
        this.restUserEntityMockMvc = MockMvcBuilders.standaloneSetup(userResource)
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
    public static UserEntity createEntity(EntityManager em) {
        UserEntity userEntity = new UserEntity()
            .login(DEFAULT_LOGIN)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .activated(DEFAULT_ACTIVATED)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE);
        return userEntity;
    }

    @Before
    public void initTest() {
        userEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserEntity() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the UserEntity
        restUserEntityMockMvc.perform(post("/api/user-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEntity)))
            .andExpect(status().isCreated());

        // Validate the UserEntity in the database
        List<UserEntity> userEntityList = userRepository.findAll();
        assertThat(userEntityList).hasSize(databaseSizeBeforeCreate + 1);
        UserEntity testUserEntity = userEntityList.get(userEntityList.size() - 1);
        assertThat(testUserEntity.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUserEntity.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testUserEntity.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserEntity.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserEntity.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserEntity.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testUserEntity.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testUserEntity.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testUserEntity.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
    }

    @Test
    @Transactional
    public void createUserEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the UserEntity with an existing ID
        userEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserEntityMockMvc.perform(post("/api/user-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEntity)))
            .andExpect(status().isBadRequest());

        // Validate the UserEntity in the database
        List<UserEntity> userEntityList = userRepository.findAll();
        assertThat(userEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserEntities() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(userEntity);

        // Get all the userEntityList
        restUserEntityMockMvc.perform(get("/api/user-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(sameInstant(DEFAULT_RESET_DATE))));
    }

    @Test
    @Transactional
    public void getUserEntity() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(userEntity);

        // Get the userEntity
        restUserEntityMockMvc.perform(get("/api/user-entities/{id}", userEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userEntity.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY.toString()))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY.toString()))
            .andExpect(jsonPath("$.resetDate").value(sameInstant(DEFAULT_RESET_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingUserEntity() throws Exception {
        // Get the userEntity
        restUserEntityMockMvc.perform(get("/api/user-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserEntity() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(userEntity);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the userEntity
        UserEntity updatedUserEntity = userRepository.findOne(userEntity.getId());
        // Disconnect from session so that the updates on updatedUserEntity are not directly saved in db
        em.detach(updatedUserEntity);
        updatedUserEntity
            .login(UPDATED_LOGIN)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE);

        restUserEntityMockMvc.perform(put("/api/user-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserEntity)))
            .andExpect(status().isOk());

        // Validate the UserEntity in the database
        List<UserEntity> userEntityList = userRepository.findAll();
        assertThat(userEntityList).hasSize(databaseSizeBeforeUpdate);
        UserEntity testUserEntity = userEntityList.get(userEntityList.size() - 1);
        assertThat(testUserEntity.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUserEntity.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testUserEntity.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserEntity.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserEntity.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserEntity.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testUserEntity.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testUserEntity.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testUserEntity.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserEntity() throws Exception {
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Create the UserEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserEntityMockMvc.perform(put("/api/user-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEntity)))
            .andExpect(status().isCreated());

        // Validate the UserEntity in the database
        List<UserEntity> userEntityList = userRepository.findAll();
        assertThat(userEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserEntity() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(userEntity);
        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Get the userEntity
        restUserEntityMockMvc.perform(delete("/api/user-entities/{id}", userEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserEntity> userEntityList = userRepository.findAll();
        assertThat(userEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserEntity.class);
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(userEntity1.getId());
        assertThat(userEntity1).isEqualTo(userEntity2);
        userEntity2.setId(2L);
        assertThat(userEntity1).isNotEqualTo(userEntity2);
        userEntity1.setId(null);
        assertThat(userEntity1).isNotEqualTo(userEntity2);
    }
}
