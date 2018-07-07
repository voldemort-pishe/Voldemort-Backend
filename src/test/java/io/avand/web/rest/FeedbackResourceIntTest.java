package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.FeedbackEntity;
import io.avand.repository.FeedbackRepository;
import io.avand.service.FeedbackService;
import io.avand.service.dto.FeedbackDTO;
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

import io.avand.domain.enumeration.FeedbackRate;

/**
 * Test class for the FeedbackEntityResource REST controller.
 *
 * @see FeedbackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class FeedbackResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_FEEDBACK_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK_TEXT = "BBBBBBBBBB";

    private static final FeedbackRate DEFAULT_RATING = FeedbackRate.STRONG_NEGETIVE;
    private static final FeedbackRate UPDATED_RATING = FeedbackRate.NEGETIVE;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeedbackEntityMockMvc;

    private FeedbackDTO feedbackEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeedbackResource feedbackResource = new FeedbackResource(feedbackService);
        this.restFeedbackEntityMockMvc = MockMvcBuilders.standaloneSetup(feedbackResource)
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
    public static FeedbackDTO createEntity(EntityManager em) {
        FeedbackDTO feedbackEntity = new FeedbackDTO();
        feedbackEntity.setFeedbackText(DEFAULT_FEEDBACK_TEXT);
        feedbackEntity.setRating(DEFAULT_RATING);
        return feedbackEntity;
    }

    @Before
    public void initTest() {
        feedbackEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedbackEntity() throws Exception {
        int databaseSizeBeforeCreate = feedbackService.findAll().size();

        // Create the FeedbackEntity
        restFeedbackEntityMockMvc.perform(post("/api/feedback")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedbackEntity)))
            .andExpect(status().isCreated());

        // Validate the FeedbackEntity in the database
        List<FeedbackDTO> feedbackEntityList = feedbackService.findAll();
        assertThat(feedbackEntityList).hasSize(databaseSizeBeforeCreate + 1);
        FeedbackDTO testFeedbackEntity = feedbackEntityList.get(feedbackEntityList.size() - 1);
        assertThat(testFeedbackEntity.getFeedbackText()).isEqualTo(DEFAULT_FEEDBACK_TEXT);
        assertThat(testFeedbackEntity.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createFeedbackEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedbackService.findAll().size();

        // Create the FeedbackEntity with an existing ID
        feedbackEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackEntityMockMvc.perform(post("/api/feedback")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedbackEntity)))
            .andExpect(status().isBadRequest());

        // Validate the FeedbackEntity in the database
        List<FeedbackDTO> feedbackEntityList = feedbackService.findAll();
        assertThat(feedbackEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFeedbackEntities() throws Exception {
        // Initialize the database
        feedbackService.save(feedbackEntity);

        // Get all the feedbackEntityList
        restFeedbackEntityMockMvc.perform(get("/api/feedback?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].feedbackText").value(hasItem(DEFAULT_FEEDBACK_TEXT.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())));
    }

    @Test
    @Transactional
    public void getFeedbackEntity() throws Exception {
        // Initialize the database
        feedbackService.save(feedbackEntity);

        // Get the feedbackEntity
        restFeedbackEntityMockMvc.perform(get("/api/feedback/{id}", feedbackEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feedbackEntity.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.feedbackText").value(DEFAULT_FEEDBACK_TEXT.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeedbackEntity() throws Exception {
        // Get the feedbackEntity
        restFeedbackEntityMockMvc.perform(get("/api/feedback/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedbackEntity() throws Exception {
        // Initialize the database
        feedbackService.save(feedbackEntity);
        int databaseSizeBeforeUpdate = feedbackService.findAll().size();

        // Update the feedbackEntity
        FeedbackDTO updatedFeedbackEntity = feedbackService.findById(feedbackEntity.getId());
        // Disconnect from session so that the updates on updatedFeedbackEntity are not directly saved in db
        em.detach(updatedFeedbackEntity);
        updatedFeedbackEntity.setFeedbackText(UPDATED_FEEDBACK_TEXT);
        updatedFeedbackEntity.setRating(UPDATED_RATING);

        restFeedbackEntityMockMvc.perform(put("/api/feedback")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeedbackEntity)))
            .andExpect(status().isOk());

        // Validate the FeedbackEntity in the database
        List<FeedbackDTO> feedbackEntityList = feedbackService.findAll();
        assertThat(feedbackEntityList).hasSize(databaseSizeBeforeUpdate);
        FeedbackDTO testFeedbackEntity = feedbackEntityList.get(feedbackEntityList.size() - 1);
        assertThat(testFeedbackEntity.getFeedbackText()).isEqualTo(UPDATED_FEEDBACK_TEXT);
        assertThat(testFeedbackEntity.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedbackEntity() throws Exception {
        int databaseSizeBeforeUpdate = feedbackService.findAll().size();

        // Create the FeedbackEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeedbackEntityMockMvc.perform(put("/api/feedback")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedbackEntity)))
            .andExpect(status().isCreated());

        // Validate the FeedbackEntity in the database
        List<FeedbackDTO> feedbackEntityList = feedbackService.findAll();
        assertThat(feedbackEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFeedbackEntity() throws Exception {
        // Initialize the database
        feedbackService.save(feedbackEntity);
        int databaseSizeBeforeDelete = feedbackService.findAll().size();

        // Get the feedbackEntity
        restFeedbackEntityMockMvc.perform(delete("/api/feedback/{id}", feedbackEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FeedbackDTO> feedbackEntityList = feedbackService.findAll();
        assertThat(feedbackEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackEntity.class);
        FeedbackEntity feedbackEntity1 = new FeedbackEntity();
        feedbackEntity1.setId(1L);
        FeedbackEntity feedbackEntity2 = new FeedbackEntity();
        feedbackEntity2.setId(feedbackEntity1.getId());
        assertThat(feedbackEntity1).isEqualTo(feedbackEntity2);
        feedbackEntity2.setId(2L);
        assertThat(feedbackEntity1).isNotEqualTo(feedbackEntity2);
        feedbackEntity1.setId(null);
        assertThat(feedbackEntity1).isNotEqualTo(feedbackEntity2);
    }
}
