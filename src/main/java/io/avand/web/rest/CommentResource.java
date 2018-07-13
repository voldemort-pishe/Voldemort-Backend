package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.service.CommentService;
import io.avand.service.dto.CommentDTO;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/comment")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    private static final String ENTITY_NAME = "commentEntity";

    private final CommentService commentService;

    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }


    /**
     * POST  /comment : Create a new commentEntity.
     *
     * @param commentDTO the commentEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentEntity, or with status 400 (Bad Request) if the commentEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity createComment(@RequestBody CommentDTO commentDTO) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", commentDTO);
        if (commentDTO.getId() != null) {
            throw new BadRequestAlertException("A new comment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            CommentDTO result = commentService.save(commentDTO);
            return ResponseEntity.created(new URI("/api/comment-entities/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /comment : Updates an existing commentEntity.
     *
     * @param commentDTO the commentEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentEntity,
     * or with status 400 (Bad Request) if the commentEntity is not valid,
     * or with status 500 (Internal Server Error) if the commentEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity updateComment(@RequestBody CommentDTO commentDTO) throws URISyntaxException {
        log.debug("REST request to update Comment : {}", commentDTO);
        if (commentDTO.getId() == null) {
            return createComment(commentDTO);
        }
        try {
            CommentDTO result = commentService.save(commentDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /comment : get all the commentEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commentEntities in body
     */
    @GetMapping
    @Timed
    public ResponseEntity getAllCommentEntities() {
        log.debug("REST request to get all Comment");
        List<CommentDTO> commentDTOS = commentService.findAll();
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    /**
     * GET  /comment/:id : get the "id" commentEntity.
     *
     * @param id the id of the commentEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentEntity, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity getComment(@PathVariable Long id) {
        log.debug("REST request to get Comment : {}", id);
        try {
            CommentDTO commentDTO = commentService.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentDTO));
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /comment/:id : delete the "id" commentEntity.
     *
     * @param id the id of the commentEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.debug("REST request to delete Comment : {}", id);
        commentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
