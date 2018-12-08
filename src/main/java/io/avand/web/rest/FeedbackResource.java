package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.security.AuthoritiesConstants;
import io.avand.service.FeedbackService;
import io.avand.service.dto.FeedbackDTO;
import io.avand.web.rest.component.FeedbackComponent;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.avand.web.rest.vm.response.ResponseVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

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

    private final FeedbackComponent feedbackComponent;

    public FeedbackResource(FeedbackService feedbackService,
                            FeedbackComponent feedbackComponent) {
        this.feedbackService = feedbackService;
        this.feedbackComponent = feedbackComponent;
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
    @PreAuthorize("isMember(#feedbackDTO.candidateId,'CANDIDATE','ADD_FEEDBACK')")
    public ResponseEntity<ResponseVM<FeedbackDTO>> createFeedback(@RequestBody FeedbackDTO feedbackDTO)
        throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            ResponseVM<FeedbackDTO> result = feedbackComponent.save(feedbackDTO);
            return ResponseEntity.created(new URI("/api/feedback/" + result.getData().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
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
    @PreAuthorize("isMember(#feedbackDTO.candidateId,'CANDIDATE','EDIT_FEEDBACK')")
    public ResponseEntity<ResponseVM<FeedbackDTO>> updateFeedback(@RequestBody FeedbackDTO feedbackDTO)
        throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() == null) {
            return createFeedback(feedbackDTO);
        }
        try {
            ResponseVM<FeedbackDTO> result = feedbackComponent.update(feedbackDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feedbackDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /feedback/:id : get the "id" feedbackEntity.
     *
     * @param id the id of the feedbackEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedbackEntity, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'FEEDBACK','VIEW_FEEDBACK')")
    public ResponseEntity<ResponseVM<FeedbackDTO>> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        try {
            ResponseVM<FeedbackDTO> feedbackDTO = feedbackComponent.findById(id);
            return new ResponseEntity<>(feedbackDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }

    }

    /**
     * GET  /feedback/candidate-feedback/{id} : get all the feedbackEntities by candidate id.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feedbackEntities in body
     */
    @GetMapping("/candidate/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'CANDIDATE','VIEW_FEEDBACK')")
    public ResponseEntity<Page<ResponseVM<FeedbackDTO>>> getAllFeedbackByCandidate(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get all Feedback");
        try {
            Page<ResponseVM<FeedbackDTO>> feedbackDTOS = feedbackComponent.findAllByCandidate(id, pageable);
            return new ResponseEntity<>(feedbackDTOS, HttpStatus.OK);
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
    @PreAuthorize("isMember(#id,'FEEDBACK','DELETE_FEEDBACK')")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
