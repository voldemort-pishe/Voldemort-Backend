package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.entity.jpa.CommentEntity;
import io.avand.service.CommentService;
import io.avand.service.dto.CommentDTO;
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
 * Test class for the CommentEntityResource REST controller.
 *
 * @see CommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class CommentResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_COMMENT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentEntityMockMvc;

    private CommentDTO commentEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentResource commentResource = new CommentResource(commentService);
        this.restCommentEntityMockMvc = MockMvcBuilders.standaloneSetup(commentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentDTO createEntity(EntityManager em) {
        CommentDTO commentEntity = new CommentDTO();
        commentEntity.setCommentText(DEFAULT_COMMENT_TEXT);
        commentEntity.setStatus(DEFAULT_STATUS);
        return commentEntity;
    }

    @Before
    public void initTest() {
        commentEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentEntity() throws Exception {
        int databaseSizeBeforeCreate = commentService.findAll().size();

        // Create the CommentEntity
        restCommentEntityMockMvc.perform(post("/api/comment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentEntity)))
            .andExpect(status().isCreated());

        // Validate the CommentEntity in the database
        List<CommentDTO> commentEntityList = commentService.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CommentDTO testCommentEntity = commentEntityList.get(commentEntityList.size() - 1);
        assertThat(testCommentEntity.getCommentText()).isEqualTo(DEFAULT_COMMENT_TEXT);
        assertThat(testCommentEntity.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCommentEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentService.findAll().size();

        // Create the CommentEntity with an existing ID
        commentEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentEntityMockMvc.perform(post("/api/comment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CommentEntity in the database
        List<CommentDTO> commentEntityList = commentService.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommentEntities() throws Exception {
        // Initialize the database
        commentService.save(commentEntity);

        // Get all the commentEntityList
        restCommentEntityMockMvc.perform(get("/api/comment?sort=id,desc"))
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
        commentService.save(commentEntity);

        // Get the commentEntity
        restCommentEntityMockMvc.perform(get("/api/comment/{id}", commentEntity.getId()))
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
        restCommentEntityMockMvc.perform(get("/api/comment/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentEntity() throws Exception {
        // Initialize the database
        commentService.save(commentEntity);
        int databaseSizeBeforeUpdate = commentService.findAll().size();

        // Update the commentEntity
        CommentDTO updatedCommentEntity = commentService.findById(commentEntity.getId());
        // Disconnect from session so that the updates on updatedCommentEntity are not directly saved in db
        em.detach(updatedCommentEntity);
        updatedCommentEntity.setCommentText(UPDATED_COMMENT_TEXT);
        updatedCommentEntity.setStatus(UPDATED_STATUS);

        restCommentEntityMockMvc.perform(put("/api/comment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentEntity)))
            .andExpect(status().isOk());

        // Validate the CommentEntity in the database
        List<CommentDTO> commentEntityList = commentService.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeUpdate);
        CommentDTO testCommentEntity = commentEntityList.get(commentEntityList.size() - 1);
        assertThat(testCommentEntity.getCommentText()).isEqualTo(UPDATED_COMMENT_TEXT);
        assertThat(testCommentEntity.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentEntity() throws Exception {
        int databaseSizeBeforeUpdate = commentService.findAll().size();

        // Create the CommentEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentEntityMockMvc.perform(put("/api/comment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentEntity)))
            .andExpect(status().isCreated());

        // Validate the CommentEntity in the database
        List<CommentDTO> commentEntityList = commentService.findAll();
        assertThat(commentEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommentEntity() throws Exception {
        // Initialize the database
        commentService.save(commentEntity);
        int databaseSizeBeforeDelete = commentService.findAll().size();

        // Get the commentEntity
        restCommentEntityMockMvc.perform(delete("/api/comment/{id}", commentEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommentDTO> commentEntityList = commentService.findAll();
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
