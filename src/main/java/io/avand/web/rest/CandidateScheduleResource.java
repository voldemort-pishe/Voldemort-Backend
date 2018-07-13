package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.entity.jpa.CandidateScheduleEntity;

import io.avand.repository.jpa.CandidateScheduleRepository;
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
 * REST controller for managing CandidateScheduleEntity.
 */
@RestController
@RequestMapping("/api")
public class CandidateScheduleResource {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleResource.class);

    private static final String ENTITY_NAME = "candidateScheduleEntity";

    private final CandidateScheduleRepository candidateScheduleRepository;

    public CandidateScheduleResource(CandidateScheduleRepository candidateScheduleRepository) {
        this.candidateScheduleRepository = candidateScheduleRepository;
    }

    /**
     * POST  /candidate-schedule-entities : Create a new candidateScheduleEntity.
     *
     * @param candidateScheduleEntity the candidateScheduleEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateScheduleEntity, or with status 400 (Bad Request) if the candidateScheduleEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-schedule-entities")
    @Timed
    public ResponseEntity<CandidateScheduleEntity> createCandidateScheduleEntity(@RequestBody CandidateScheduleEntity candidateScheduleEntity) throws URISyntaxException {
        log.debug("REST request to save CandidateScheduleEntity : {}", candidateScheduleEntity);
        if (candidateScheduleEntity.getId() != null) {
            throw new BadRequestAlertException("A new candidateScheduleEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateScheduleEntity result = candidateScheduleRepository.save(candidateScheduleEntity);
        return ResponseEntity.created(new URI("/api/candidate-schedule-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-schedule-entities : Updates an existing candidateScheduleEntity.
     *
     * @param candidateScheduleEntity the candidateScheduleEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateScheduleEntity,
     * or with status 400 (Bad Request) if the candidateScheduleEntity is not valid,
     * or with status 500 (Internal Server Error) if the candidateScheduleEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-schedule-entities")
    @Timed
    public ResponseEntity<CandidateScheduleEntity> updateCandidateScheduleEntity(@RequestBody CandidateScheduleEntity candidateScheduleEntity) throws URISyntaxException {
        log.debug("REST request to update CandidateScheduleEntity : {}", candidateScheduleEntity);
        if (candidateScheduleEntity.getId() == null) {
            return createCandidateScheduleEntity(candidateScheduleEntity);
        }
        CandidateScheduleEntity result = candidateScheduleRepository.save(candidateScheduleEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateScheduleEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-schedule-entities : get all the candidateScheduleEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateScheduleEntities in body
     */
    @GetMapping("/candidate-schedule-entities")
    @Timed
    public List<CandidateScheduleEntity> getAllCandidateScheduleEntities() {
        log.debug("REST request to get all CandidateScheduleEntities");
        return candidateScheduleRepository.findAll();
        }

    /**
     * GET  /candidate-schedule-entities/:id : get the "id" candidateScheduleEntity.
     *
     * @param id the id of the candidateScheduleEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateScheduleEntity, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-schedule-entities/{id}")
    @Timed
    public ResponseEntity<CandidateScheduleEntity> getCandidateScheduleEntity(@PathVariable Long id) {
        log.debug("REST request to get CandidateScheduleEntity : {}", id);
        CandidateScheduleEntity candidateScheduleEntity = candidateScheduleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateScheduleEntity));
    }

    /**
     * DELETE  /candidate-schedule-entities/:id : delete the "id" candidateScheduleEntity.
     *
     * @param id the id of the candidateScheduleEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-schedule-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateScheduleEntity(@PathVariable Long id) {
        log.debug("REST request to delete CandidateScheduleEntity : {}", id);
        candidateScheduleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
