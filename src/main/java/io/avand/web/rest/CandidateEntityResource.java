package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.CandidateEntity;

import io.avand.repository.CandidateEntityRepository;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing CandidateEntity.
 */
@RestController
@RequestMapping("/api")
public class CandidateEntityResource {

    private final Logger log = LoggerFactory.getLogger(CandidateEntityResource.class);

    private static final String ENTITY_NAME = "candidateEntity";

    private final CandidateEntityRepository candidateEntityRepository;

    public CandidateEntityResource(CandidateEntityRepository candidateEntityRepository) {
        this.candidateEntityRepository = candidateEntityRepository;
    }

    /**
     * POST  /candidate-entities : Create a new candidateEntity.
     *
     * @param candidateEntity the candidateEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateEntity, or with status 400 (Bad Request) if the candidateEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-entities")
    @Timed
    public ResponseEntity<CandidateEntity> createCandidateEntity(@Valid @RequestBody CandidateEntity candidateEntity) throws URISyntaxException {
        log.debug("REST request to save CandidateEntity : {}", candidateEntity);
        if (candidateEntity.getId() != null) {
            throw new BadRequestAlertException("A new candidateEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateEntity result = candidateEntityRepository.save(candidateEntity);
        return ResponseEntity.created(new URI("/api/candidate-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-entities : Updates an existing candidateEntity.
     *
     * @param candidateEntity the candidateEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateEntity,
     * or with status 400 (Bad Request) if the candidateEntity is not valid,
     * or with status 500 (Internal Server Error) if the candidateEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-entities")
    @Timed
    public ResponseEntity<CandidateEntity> updateCandidateEntity(@Valid @RequestBody CandidateEntity candidateEntity) throws URISyntaxException {
        log.debug("REST request to update CandidateEntity : {}", candidateEntity);
        if (candidateEntity.getId() == null) {
            return createCandidateEntity(candidateEntity);
        }
        CandidateEntity result = candidateEntityRepository.save(candidateEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-entities : get all the candidateEntities.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of candidateEntities in body
     */
    @GetMapping("/candidate-entities")
    @Timed
    public List<CandidateEntity> getAllCandidateEntities(@RequestParam(required = false) String filter) {
        if ("job-is-null".equals(filter)) {
            log.debug("REST request to get all CandidateEntitys where job is null");
            return StreamSupport
                .stream(candidateEntityRepository.findAll().spliterator(), false)
                .filter(candidateEntity -> candidateEntity.getJob() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all CandidateEntities");
        return candidateEntityRepository.findAll();
        }

    /**
     * GET  /candidate-entities/:id : get the "id" candidateEntity.
     *
     * @param id the id of the candidateEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateEntity, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-entities/{id}")
    @Timed
    public ResponseEntity<CandidateEntity> getCandidateEntity(@PathVariable Long id) {
        log.debug("REST request to get CandidateEntity : {}", id);
        CandidateEntity candidateEntity = candidateEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateEntity));
    }

    /**
     * DELETE  /candidate-entities/:id : delete the "id" candidateEntity.
     *
     * @param id the id of the candidateEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateEntity(@PathVariable Long id) {
        log.debug("REST request to delete CandidateEntity : {}", id);
        candidateEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
