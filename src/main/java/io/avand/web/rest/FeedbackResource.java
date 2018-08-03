package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.service.FeedbackService;
import io.avand.service.dto.FeedbackDTO;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/feedback")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedbackEntity";

    private final FeedbackService feedbackService;

    public FeedbackResource(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    /**
     * POST  /feedback : Create a new feedback.
     *
     * @param feedbackDTO the feedback to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feedbackEntity, or with status 400 (Bad Request) if the feedbackEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity createFeedback(@RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            FeedbackDTO result = feedbackService.save(feedbackDTO);
            return ResponseEntity.created(new URI("/api/feedback/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException | IllegalStateException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /feedback : Updates an existing feedback.
     *
     * @param feedbackDTO the feedback to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feedbackEntity,
     * or with status 400 (Bad Request) if the feedbackEntity is not valid,
     * or with status 500 (Internal Server Error) if the feedbackEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity updateFeedback(@RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() == null) {
            return createFeedback(feedbackDTO);
        }
        try {
            FeedbackDTO result = feedbackService.update(feedbackDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feedbackDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /feedback : get all the feedbackEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feedbackEntities in body
     */
    @GetMapping
    @Timed
    public ResponseEntity getAllFeedback(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Feedback");
        Page<FeedbackDTO> feedbackDTOS = feedbackService.findAll(pageable);
        return new ResponseEntity<>(feedbackDTOS, HttpStatus.OK);
    }

    /**
     * GET  /feedback/:id : get the "id" feedbackEntity.
     *
     * @param id the id of the feedbackEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedbackEntity, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        try {
            FeedbackDTO feedbackDTO = feedbackService.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(feedbackDTO));
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }

    }

    /**
     * DELETE  /feedback/:id : delete the "id" feedbackEntity.
     *
     * @param id the id of the feedbackEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /feedback : get all the feedbackEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feedbackEntities in body
     */
    @GetMapping("/candidate-feedback/{id}")
    @Timed
    public ResponseEntity getAllFeedbackByCandidate(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get all Feedback");
        Page<FeedbackDTO> feedbackDTOS = feedbackService.findAllByCandidateId(pageable, id);
        return new ResponseEntity<>(feedbackDTOS, HttpStatus.OK);
    }

}
