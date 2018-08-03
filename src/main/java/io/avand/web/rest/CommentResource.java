package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.config.ApplicationProperties;
import io.avand.service.CandidateService;
import io.avand.service.CommentService;
import io.avand.service.UserService;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CommentDTO;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.avand.web.rest.vm.CommentCandidateVM;
import io.avand.web.rest.vm.CommentUserVM;
import io.avand.web.rest.vm.CommentVM;
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

import java.util.ArrayList;
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

    private final UserService userService;

    private final CandidateService candidateService;

    private final ApplicationProperties applicationProperties;

    public CommentResource(CommentService commentService,
                           UserService userService,
                           CandidateService candidateService,
                           ApplicationProperties applicationProperties) {
        this.commentService = commentService;
        this.userService = userService;
        this.candidateService = candidateService;
        this.applicationProperties = applicationProperties;
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
    public ResponseEntity getAllCommentEntities(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Comment");
        Page<CommentDTO> commentDTOS = commentService.findAll(pageable);
        List<CommentVM> commentVMS = new ArrayList<>();
        for (CommentDTO commentDTO : commentDTOS) {
            try {
                commentVMS.add(createCommentFromDTO(commentDTO));
            } catch (NotFoundException ignore) {
            }
        }
        return new ResponseEntity<>(commentVMS, HttpStatus.OK);
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
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(createCommentFromDTO(commentService.findById(id))));
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

    private CommentVM createCommentFromDTO(CommentDTO commentDTO) throws NotFoundException {
        CommentVM commentVM = new CommentVM();
        commentVM.setId(commentDTO.getId());
        commentVM.setCommentText(commentDTO.getCommentText());
        commentVM.setStatus(commentDTO.getStatus());

        Optional<UserDTO> userDTOOptional = userService.findById(commentDTO.getUserId());
        if (userDTOOptional.isPresent()){
            CommentUserVM commentUserVM = new CommentUserVM();
            commentUserVM.setName(userDTOOptional.get().getFirstName() + " "+ userDTOOptional.get().getLastName());
            commentVM.setUser(commentUserVM);
        }
        CandidateDTO candidateDTO = candidateService.findById(commentDTO.getCandidateId());
        CommentCandidateVM commentCandidateVM = new CommentCandidateVM();
        commentCandidateVM.setName(candidateDTO.getFirstName() + " "+ candidateDTO.getLastName());
        commentCandidateVM.setFilePath(applicationProperties.getBase().getUrl() + "api/file/load/" + candidateDTO.getFileId());
        commentVM.setCandidate(commentCandidateVM);
        return commentVM;
    }
}
