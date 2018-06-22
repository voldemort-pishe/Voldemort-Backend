package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.CommentEntity;

import io.avand.repository.CommentRepository;
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
 * REST controller for managing CommentEntity.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    private static final String ENTITY_NAME = "commentEntity";

    private final CommentRepository commentRepository;

    public CommentResource(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * POST  /comment-entities : Create a new commentEntity.
     *
     * @param commentEntity the commentEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentEntity, or with status 400 (Bad Request) if the commentEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comment-entities")
    @Timed
    public ResponseEntity<CommentEntity> createCommentEntity(@RequestBody CommentEntity commentEntity) throws URISyntaxException {
        log.debug("REST request to save CommentEntity : {}", commentEntity);
        if (commentEntity.getId() != null) {
            throw new BadRequestAlertException("A new commentEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentEntity result = commentRepository.save(commentEntity);
        return ResponseEntity.created(new URI("/api/comment-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comment-entities : Updates an existing commentEntity.
     *
     * @param commentEntity the commentEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentEntity,
     * or with status 400 (Bad Request) if the commentEntity is not valid,
     * or with status 500 (Internal Server Error) if the commentEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comment-entities")
    @Timed
    public ResponseEntity<CommentEntity> updateCommentEntity(@RequestBody CommentEntity commentEntity) throws URISyntaxException {
        log.debug("REST request to update CommentEntity : {}", commentEntity);
        if (commentEntity.getId() == null) {
            return createCommentEntity(commentEntity);
        }
        CommentEntity result = commentRepository.save(commentEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comment-entities : get all the commentEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commentEntities in body
     */
    @GetMapping("/comment-entities")
    @Timed
    public List<CommentEntity> getAllCommentEntities() {
        log.debug("REST request to get all CommentEntities");
        return commentRepository.findAll();
        }

    /**
     * GET  /comment-entities/:id : get the "id" commentEntity.
     *
     * @param id the id of the commentEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentEntity, or with status 404 (Not Found)
     */
    @GetMapping("/comment-entities/{id}")
    @Timed
    public ResponseEntity<CommentEntity> getCommentEntity(@PathVariable Long id) {
        log.debug("REST request to get CommentEntity : {}", id);
        CommentEntity commentEntity = commentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentEntity));
    }

    /**
     * DELETE  /comment-entities/:id : delete the "id" commentEntity.
     *
     * @param id the id of the commentEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comment-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentEntity(@PathVariable Long id) {
        log.debug("REST request to delete CommentEntity : {}", id);
        commentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
