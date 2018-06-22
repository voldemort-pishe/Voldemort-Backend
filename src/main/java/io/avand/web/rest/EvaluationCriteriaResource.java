package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.EvaluationCriteriaEntity;

import io.avand.repository.EvaluationCriteriaRepository;
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
 * REST controller for managing EvaluationCriteriaEntity.
 */
@RestController
@RequestMapping("/api")
public class EvaluationCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationCriteriaResource.class);

    private static final String ENTITY_NAME = "evaluationCriteriaEntity";

    private final EvaluationCriteriaRepository evaluationCriteriaRepository;

    public EvaluationCriteriaResource(EvaluationCriteriaRepository evaluationCriteriaRepository) {
        this.evaluationCriteriaRepository = evaluationCriteriaRepository;
    }

    /**
     * POST  /evaluation-criteria-entities : Create a new evaluationCriteriaEntity.
     *
     * @param evaluationCriteriaEntity the evaluationCriteriaEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationCriteriaEntity, or with status 400 (Bad Request) if the evaluationCriteriaEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-criteria-entities")
    @Timed
    public ResponseEntity<EvaluationCriteriaEntity> createEvaluationCriteriaEntity(@RequestBody EvaluationCriteriaEntity evaluationCriteriaEntity) throws URISyntaxException {
        log.debug("REST request to save EvaluationCriteriaEntity : {}", evaluationCriteriaEntity);
        if (evaluationCriteriaEntity.getId() != null) {
            throw new BadRequestAlertException("A new evaluationCriteriaEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationCriteriaEntity result = evaluationCriteriaRepository.save(evaluationCriteriaEntity);
        return ResponseEntity.created(new URI("/api/evaluation-criteria-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluation-criteria-entities : Updates an existing evaluationCriteriaEntity.
     *
     * @param evaluationCriteriaEntity the evaluationCriteriaEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationCriteriaEntity,
     * or with status 400 (Bad Request) if the evaluationCriteriaEntity is not valid,
     * or with status 500 (Internal Server Error) if the evaluationCriteriaEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-criteria-entities")
    @Timed
    public ResponseEntity<EvaluationCriteriaEntity> updateEvaluationCriteriaEntity(@RequestBody EvaluationCriteriaEntity evaluationCriteriaEntity) throws URISyntaxException {
        log.debug("REST request to update EvaluationCriteriaEntity : {}", evaluationCriteriaEntity);
        if (evaluationCriteriaEntity.getId() == null) {
            return createEvaluationCriteriaEntity(evaluationCriteriaEntity);
        }
        EvaluationCriteriaEntity result = evaluationCriteriaRepository.save(evaluationCriteriaEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluationCriteriaEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluation-criteria-entities : get all the evaluationCriteriaEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationCriteriaEntities in body
     */
    @GetMapping("/evaluation-criteria-entities")
    @Timed
    public List<EvaluationCriteriaEntity> getAllEvaluationCriteriaEntities() {
        log.debug("REST request to get all EvaluationCriteriaEntities");
        return evaluationCriteriaRepository.findAll();
        }

    /**
     * GET  /evaluation-criteria-entities/:id : get the "id" evaluationCriteriaEntity.
     *
     * @param id the id of the evaluationCriteriaEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationCriteriaEntity, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-criteria-entities/{id}")
    @Timed
    public ResponseEntity<EvaluationCriteriaEntity> getEvaluationCriteriaEntity(@PathVariable Long id) {
        log.debug("REST request to get EvaluationCriteriaEntity : {}", id);
        EvaluationCriteriaEntity evaluationCriteriaEntity = evaluationCriteriaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(evaluationCriteriaEntity));
    }

    /**
     * DELETE  /evaluation-criteria-entities/:id : delete the "id" evaluationCriteriaEntity.
     *
     * @param id the id of the evaluationCriteriaEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-criteria-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluationCriteriaEntity(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationCriteriaEntity : {}", id);
        evaluationCriteriaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
