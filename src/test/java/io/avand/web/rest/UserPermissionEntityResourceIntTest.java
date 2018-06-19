package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.UserPermissionEntity;
import io.avand.repository.UserPermissionEntityRepository;
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

import io.avand.domain.enumeration.PermissionAction;
/**
 * Test class for the UserPermissionEntityResource REST controller.
 *
 * @see UserPermissionEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class UserPermissionEntityResourceIntTest {

    private static final PermissionAction DEFAULT_ACTION = PermissionAction.READ;
    private static final PermissionAction UPDATED_ACTION = PermissionAction.WRITE;

    @Autowired
    private UserPermissionEntityRepository userPermissionEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserPermissionEntityMockMvc;

    private UserPermissionEntity userPermissionEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPermissionEntityResource userPermissionEntityResource = new UserPermissionEntityResource(userPermissionEntityRepository);
        this.restUserPermissionEntityMockMvc = MockMvcBuilders.standaloneSetup(userPermissionEntityResource)
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
    public static UserPermissionEntity createEntity(EntityManager em) {
        UserPermissionEntity userPermissionEntity = new UserPermissionEntity()
            .action(DEFAULT_ACTION);
        return userPermissionEntity;
    }

    @Before
    public void initTest() {
        userPermissionEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPermissionEntity() throws Exception {
        int databaseSizeBeforeCreate = userPermissionEntityRepository.findAll().size();

        // Create the UserPermissionEntity
        restUserPermissionEntityMockMvc.perform(post("/api/user-permission-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionEntity)))
            .andExpect(status().isCreated());

        // Validate the UserPermissionEntity in the database
        List<UserPermissionEntity> userPermissionEntityList = userPermissionEntityRepository.findAll();
        assertThat(userPermissionEntityList).hasSize(databaseSizeBeforeCreate + 1);
        UserPermissionEntity testUserPermissionEntity = userPermissionEntityList.get(userPermissionEntityList.size() - 1);
        assertThat(testUserPermissionEntity.getAction()).isEqualTo(DEFAULT_ACTION);
    }

    @Test
    @Transactional
    public void createUserPermissionEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPermissionEntityRepository.findAll().size();

        // Create the UserPermissionEntity with an existing ID
        userPermissionEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPermissionEntityMockMvc.perform(post("/api/user-permission-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionEntity)))
            .andExpect(status().isBadRequest());

        // Validate the UserPermissionEntity in the database
        List<UserPermissionEntity> userPermissionEntityList = userPermissionEntityRepository.findAll();
        assertThat(userPermissionEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserPermissionEntities() throws Exception {
        // Initialize the database
        userPermissionEntityRepository.saveAndFlush(userPermissionEntity);

        // Get all the userPermissionEntityList
        restUserPermissionEntityMockMvc.perform(get("/api/user-permission-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPermissionEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())));
    }

    @Test
    @Transactional
    public void getUserPermissionEntity() throws Exception {
        // Initialize the database
        userPermissionEntityRepository.saveAndFlush(userPermissionEntity);

        // Get the userPermissionEntity
        restUserPermissionEntityMockMvc.perform(get("/api/user-permission-entities/{id}", userPermissionEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPermissionEntity.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPermissionEntity() throws Exception {
        // Get the userPermissionEntity
        restUserPermissionEntityMockMvc.perform(get("/api/user-permission-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPermissionEntity() throws Exception {
        // Initialize the database
        userPermissionEntityRepository.saveAndFlush(userPermissionEntity);
        int databaseSizeBeforeUpdate = userPermissionEntityRepository.findAll().size();

        // Update the userPermissionEntity
        UserPermissionEntity updatedUserPermissionEntity = userPermissionEntityRepository.findOne(userPermissionEntity.getId());
        // Disconnect from session so that the updates on updatedUserPermissionEntity are not directly saved in db
        em.detach(updatedUserPermissionEntity);
        updatedUserPermissionEntity
            .action(UPDATED_ACTION);

        restUserPermissionEntityMockMvc.perform(put("/api/user-permission-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserPermissionEntity)))
            .andExpect(status().isOk());

        // Validate the UserPermissionEntity in the database
        List<UserPermissionEntity> userPermissionEntityList = userPermissionEntityRepository.findAll();
        assertThat(userPermissionEntityList).hasSize(databaseSizeBeforeUpdate);
        UserPermissionEntity testUserPermissionEntity = userPermissionEntityList.get(userPermissionEntityList.size() - 1);
        assertThat(testUserPermissionEntity.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPermissionEntity() throws Exception {
        int databaseSizeBeforeUpdate = userPermissionEntityRepository.findAll().size();

        // Create the UserPermissionEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserPermissionEntityMockMvc.perform(put("/api/user-permission-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionEntity)))
            .andExpect(status().isCreated());

        // Validate the UserPermissionEntity in the database
        List<UserPermissionEntity> userPermissionEntityList = userPermissionEntityRepository.findAll();
        assertThat(userPermissionEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserPermissionEntity() throws Exception {
        // Initialize the database
        userPermissionEntityRepository.saveAndFlush(userPermissionEntity);
        int databaseSizeBeforeDelete = userPermissionEntityRepository.findAll().size();

        // Get the userPermissionEntity
        restUserPermissionEntityMockMvc.perform(delete("/api/user-permission-entities/{id}", userPermissionEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPermissionEntity> userPermissionEntityList = userPermissionEntityRepository.findAll();
        assertThat(userPermissionEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPermissionEntity.class);
        UserPermissionEntity userPermissionEntity1 = new UserPermissionEntity();
        userPermissionEntity1.setId(1L);
        UserPermissionEntity userPermissionEntity2 = new UserPermissionEntity();
        userPermissionEntity2.setId(userPermissionEntity1.getId());
        assertThat(userPermissionEntity1).isEqualTo(userPermissionEntity2);
        userPermissionEntity2.setId(2L);
        assertThat(userPermissionEntity1).isNotEqualTo(userPermissionEntity2);
        userPermissionEntity1.setId(null);
        assertThat(userPermissionEntity1).isNotEqualTo(userPermissionEntity2);
    }
}
