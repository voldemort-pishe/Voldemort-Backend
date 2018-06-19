package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.CommentEntity;
import io.avand.repository.CommentEntityRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static io.avand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommentEntityResource REST controller.
 *
 * @see CommentEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class CommentEntityResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_COMMENT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private CommentEntityRepository commentEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentEntityMockMvc;

    private CommentEntity commentEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentEntityResource commentEntityResource = new CommentEntityResource(commentEntityRepository);
        this.restCommentEntityMockMvc = MockMvcBuilders.standaloneSetup(commentEntityResource)
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
    public static CommentEntity createEntity(EntityManager em) {
        CommentEntity commentEntity = new CommentEntity()
            .userId(DEFAULT_USER_ID)
            .commentText(DEFAULT_COMMENT_TEXT)
            .status(DEFAULT_STATUS);
        return commentEntity;
    }

    @Before
    public void initTest() {
        commentEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentEntity() throws Exception {
        int databaseSizeBeforeCreate = commentEntityRepository.findAll().size();

        // Create the CommentEntity
        restCommentEntityMockMvc.perform(post("/api/comment-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentEntity)))
            .andExpect(status().isCreated());

        // Validate the CommentEntity in the database
        List<CommentEntity> commentEntityList = commentEntityRepository.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CommentEntity testCommentEntity = commentEntityList.get(commentEntityList.size() - 1);
        assertThat(testCommentEntity.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCommentEntity.getCommentText()).isEqualTo(DEFAULT_COMMENT_TEXT);
        assertThat(testCommentEntity.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCommentEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentEntityRepository.findAll().size();

        // Create the CommentEntity with an existing ID
        commentEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentEntityMockMvc.perform(post("/api/comment-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CommentEntity in the database
        List<CommentEntity> commentEntityList = commentEntityRepository.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommentEntities() throws Exception {
        // Initialize the database
        commentEntityRepository.saveAndFlush(commentEntity);

        // Get all the commentEntityList
        restCommentEntityMockMvc.perform(get("/api/comment-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].commentText").value(hasItem(DEFAULT_COMMENT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCommentEntity() throws Exception {
        // Initialize the database
        commentEntityRepository.saveAndFlush(commentEntity);

        // Get the commentEntity
        restCommentEntityMockMvc.perform(get("/api/comment-entities/{id}", commentEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentEntity.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.commentText").value(DEFAULT_COMMENT_TEXT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCommentEntity() throws Exception {
        // Get the commentEntity
        restCommentEntityMockMvc.perform(get("/api/comment-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentEntity() throws Exception {
        // Initialize the database
        commentEntityRepository.saveAndFlush(commentEntity);
        int databaseSizeBeforeUpdate = commentEntityRepository.findAll().size();

        // Update the commentEntity
        CommentEntity updatedCommentEntity = commentEntityRepository.findOne(commentEntity.getId());
        // Disconnect from session so that the updates on updatedCommentEntity are not directly saved in db
        em.detach(updatedCommentEntity);
        updatedCommentEntity
            .userId(UPDATED_USER_ID)
            .commentText(UPDATED_COMMENT_TEXT)
            .status(UPDATED_STATUS);

        restCommentEntityMockMvc.perform(put("/api/comment-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentEntity)))
            .andExpect(status().isOk());

        // Validate the CommentEntity in the database
        List<CommentEntity> commentEntityList = commentEntityRepository.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeUpdate);
        CommentEntity testCommentEntity = commentEntityList.get(commentEntityList.size() - 1);
        assertThat(testCommentEntity.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCommentEntity.getCommentText()).isEqualTo(UPDATED_COMMENT_TEXT);
        assertThat(testCommentEntity.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentEntity() throws Exception {
        int databaseSizeBeforeUpdate = commentEntityRepository.findAll().size();

        // Create the CommentEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentEntityMockMvc.perform(put("/api/comment-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentEntity)))
            .andExpect(status().isCreated());

        // Validate the CommentEntity in the database
        List<CommentEntity> commentEntityList = commentEntityRepository.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommentEntity() throws Exception {
        // Initialize the database
        commentEntityRepository.saveAndFlush(commentEntity);
        int databaseSizeBeforeDelete = commentEntityRepository.findAll().size();

        // Get the commentEntity
        restCommentEntityMockMvc.perform(delete("/api/comment-entities/{id}", commentEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommentEntity> commentEntityList = commentEntityRepository.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentEntity.class);
        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setId(1L);
        CommentEntity commentEntity2 = new CommentEntity();
        commentEntity2.setId(commentEntity1.getId());
        assertThat(commentEntity1).isEqualTo(commentEntity2);
        commentEntity2.setId(2L);
        assertThat(commentEntity1).isNotEqualTo(commentEntity2);
        commentEntity1.setId(null);
        assertThat(commentEntity1).isNotEqualTo(commentEntity2);
    }
}
