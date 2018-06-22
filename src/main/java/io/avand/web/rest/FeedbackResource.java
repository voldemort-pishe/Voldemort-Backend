package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.FeedbackEntity;

import io.avand.repository.FeedbackRepository;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FeedbackEntity.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedbackEntity";

    private final FeedbackRepository feedbackRepository;

    public FeedbackResource(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * POST  /feedback-entities : Create a new feedbackEntity.
     *
     * @param feedbackEntity the feedbackEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feedbackEntity, or with status 400 (Bad Request) if the feedbackEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feedback-entities")
    @Timed
    public ResponseEntity<FeedbackEntity> createFeedbackEntity(@RequestBody FeedbackEntity feedbackEntity) throws URISyntaxException {
        log.debug("REST request to save FeedbackEntity : {}", feedbackEntity);
        if (feedbackEntity.getId() != null) {
            throw new BadRequestAlertException("A new feedbackEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackEntity result = feedbackRepository.save(feedbackEntity);
        return ResponseEntity.created(new URI("/api/feedback-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feedback-entities : Updates an existing feedbackEntity.
     *
     * @param feedbackEntity the feedbackEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feedbackEntity,
     * or with status 400 (Bad Request) if the feedbackEntity is not valid,
     * or with status 500 (Internal Server Error) if the feedbackEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feedback-entities")
    @Timed
    public ResponseEntity<FeedbackEntity> updateFeedbackEntity(@RequestBody FeedbackEntity feedbackEntity) throws URISyntaxException {
        log.debug("REST request to update FeedbackEntity : {}", feedbackEntity);
        if (feedbackEntity.getId() == null) {
            return createFeedbackEntity(feedbackEntity);
        }
        FeedbackEntity result = feedbackRepository.save(feedbackEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feedbackEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feedback-entities : get all the feedbackEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feedbackEntities in body
     */
    @GetMapping("/feedback-entities")
    @Timed
    public List<FeedbackEntity> getAllFeedbackEntities() {
        log.debug("REST request to get all FeedbackEntities");
        return feedbackRepository.findAll();
        }

    /**
     * GET  /feedback-entities/:id : get the "id" feedbackEntity.
     *
     * @param id the id of the feedbackEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedbackEntity, or with status 404 (Not Found)
     */
    @GetMapping("/feedback-entities/{id}")
    @Timed
    public ResponseEntity<FeedbackEntity> getFeedbackEntity(@PathVariable Long id) {
        log.debug("REST request to get FeedbackEntity : {}", id);
        FeedbackEntity feedbackEntity = feedbackRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(feedbackEntity));
    }

    /**
     * DELETE  /feedback-entities/:id : delete the "id" feedbackEntity.
     *
     * @param id the id of the feedbackEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feedback-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedbackEntity(@PathVariable Long id) {
        log.debug("REST request to delete FeedbackEntity : {}", id);
        feedbackRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
