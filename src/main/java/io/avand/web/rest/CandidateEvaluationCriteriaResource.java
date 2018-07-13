package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.entity.jpa.CandidateEvaluationCriteriaEntity;

import io.avand.repository.jpa.CandidateEvaluationCriteriaRepository;
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
 * REST controller for managing CandidateEvaluationCriteriaEntity.
 */
@RestController
@RequestMapping("/api")
public class CandidateEvaluationCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CandidateEvaluationCriteriaResource.class);

    private static final String ENTITY_NAME = "candidateEvaluationCriteriaEntity";

    private final CandidateEvaluationCriteriaRepository candidateEvaluationCriteriaRepository;

    public CandidateEvaluationCriteriaResource(CandidateEvaluationCriteriaRepository candidateEvaluationCriteriaRepository) {
        this.candidateEvaluationCriteriaRepository = candidateEvaluationCriteriaRepository;
    }

    /**
     * POST  /candidate-evaluation-criteria-entities : Create a new candidateEvaluationCriteriaEntity.
     *
     * @param candidateEvaluationCriteriaEntity the candidateEvaluationCriteriaEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateEvaluationCriteriaEntity, or with status 400 (Bad Request) if the candidateEvaluationCriteriaEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-evaluation-criteria-entities")
    @Timed
    public ResponseEntity<CandidateEvaluationCriteriaEntity> createCandidateEvaluationCriteriaEntity(@RequestBody CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity) throws URISyntaxException {
        log.debug("REST request to save CandidateEvaluationCriteriaEntity : {}", candidateEvaluationCriteriaEntity);
        if (candidateEvaluationCriteriaEntity.getId() != null) {
            throw new BadRequestAlertException("A new candidateEvaluationCriteriaEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateEvaluationCriteriaEntity result = candidateEvaluationCriteriaRepository.save(candidateEvaluationCriteriaEntity);
        return ResponseEntity.created(new URI("/api/candidate-evaluation-criteria-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-evaluation-criteria-entities : Updates an existing candidateEvaluationCriteriaEntity.
     *
     * @param candidateEvaluationCriteriaEntity the candidateEvaluationCriteriaEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateEvaluationCriteriaEntity,
     * or with status 400 (Bad Request) if the candidateEvaluationCriteriaEntity is not valid,
     * or with status 500 (Internal Server Error) if the candidateEvaluationCriteriaEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-evaluation-criteria-entities")
    @Timed
    public ResponseEntity<CandidateEvaluationCriteriaEntity> updateCandidateEvaluationCriteriaEntity(@RequestBody CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity) throws URISyntaxException {
        log.debug("REST request to update CandidateEvaluationCriteriaEntity : {}", candidateEvaluationCriteriaEntity);
        if (candidateEvaluationCriteriaEntity.getId() == null) {
            return createCandidateEvaluationCriteriaEntity(candidateEvaluationCriteriaEntity);
        }
        CandidateEvaluationCriteriaEntity result = candidateEvaluationCriteriaRepository.save(candidateEvaluationCriteriaEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateEvaluationCriteriaEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-evaluation-criteria-entities : get all the candidateEvaluationCriteriaEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateEvaluationCriteriaEntities in body
     */
    @GetMapping("/candidate-evaluation-criteria-entities")
    @Timed
    public List<CandidateEvaluationCriteriaEntity> getAllCandidateEvaluationCriteriaEntities() {
        log.debug("REST request to get all CandidateEvaluationCriteriaEntities");
        return candidateEvaluationCriteriaRepository.findAll();
        }

    /**
     * GET  /candidate-evaluation-criteria-entities/:id : get the "id" candidateEvaluationCriteriaEntity.
     *
     * @param id the id of the candidateEvaluationCriteriaEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateEvaluationCriteriaEntity, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-evaluation-criteria-entities/{id}")
    @Timed
    public ResponseEntity<CandidateEvaluationCriteriaEntity> getCandidateEvaluationCriteriaEntity(@PathVariable Long id) {
        log.debug("REST request to get CandidateEvaluationCriteriaEntity : {}", id);
        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity = candidateEvaluationCriteriaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateEvaluationCriteriaEntity));
    }

    /**
     * DELETE  /candidate-evaluation-criteria-entities/:id : delete the "id" candidateEvaluationCriteriaEntity.
     *
     * @param id the id of the candidateEvaluationCriteriaEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-evaluation-criteria-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateEvaluationCriteriaEntity(@PathVariable Long id) {
        log.debug("REST request to delete CandidateEvaluationCriteriaEntity : {}", id);
        candidateEvaluationCriteriaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
