package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.service.EvaluationCriteriaService;
import io.avand.service.dto.EvaluationCriteriaDTO;
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
 * REST controller for managing EvaluationCriteriaDTO.
 */
@RestController
@RequestMapping("/api/evaluation-criteria")
public class EvaluationCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationCriteriaResource.class);

    private static final String ENTITY_NAME = "evaluationCriteriaDTO";

    private final EvaluationCriteriaService evaluationCriteriaService;

    public EvaluationCriteriaResource(EvaluationCriteriaService evaluationCriteriaService) {
        this.evaluationCriteriaService = evaluationCriteriaService;
    }

    /**
     * POST  /evaluation-criteria : Create a new evaluationCriteriaDTO.
     *
     * @param evaluationCriteriaDTO the evaluationCriteriaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationCriteriaDTO, or with status 400 (Bad Request) if the evaluationCriteriaDTO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity<EvaluationCriteriaDTO> create(@RequestBody EvaluationCriteriaDTO evaluationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save EvaluationCriteriaDTO : {}", evaluationCriteriaDTO);
        if (evaluationCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluationCriteriaDTO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            EvaluationCriteriaDTO result = evaluationCriteriaService.save(evaluationCriteriaDTO);
            return ResponseEntity.created(new URI("/api/evaluation-criteria/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /evaluation-criteria : Updates an existing evaluationCriteriaDTO.
     *
     * @param evaluationCriteriaDTO the evaluationCriteriaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationCriteriaDTO,
     * or with status 400 (Bad Request) if the evaluationCriteriaDTO is not valid,
     * or with status 500 (Internal Server Error) if the evaluationCriteriaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity<EvaluationCriteriaDTO> update(@RequestBody EvaluationCriteriaDTO evaluationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update EvaluationCriteriaDTO : {}", evaluationCriteriaDTO);
        if (evaluationCriteriaDTO.getId() == null) {
            return create(evaluationCriteriaDTO);
        }
        try {
            EvaluationCriteriaDTO result = evaluationCriteriaService.save(evaluationCriteriaDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluationCriteriaDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /evaluation-criteria : get all the evaluationCriteriaEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationCriteriaEntities in body
     */
    @GetMapping
    @Timed
    public ResponseEntity<Page<EvaluationCriteriaDTO>> getAll(@ApiParam Pageable pageable) {
        log.debug("REST request to get all EvaluationCriteriaEntities");
        try {
            Page<EvaluationCriteriaDTO> results = evaluationCriteriaService.findAll(pageable);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /evaluation-criteria/:id : get the "id" evaluationCriteriaDTO.
     *
     * @param id the id of the evaluationCriteriaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationCriteriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<EvaluationCriteriaDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get EvaluationCriteriaDTO : {}", id);
        try {
            Optional<EvaluationCriteriaDTO> evaluationCriteriaDTO = evaluationCriteriaService.findById(id);
            return ResponseUtil.wrapOrNotFound(evaluationCriteriaDTO);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /evaluation-criteria/:id : delete the "id" evaluationCriteriaDTO.
     *
     * @param id the id of the evaluationCriteriaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationCriteriaDTO : {}", id);
        try {
            evaluationCriteriaService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
