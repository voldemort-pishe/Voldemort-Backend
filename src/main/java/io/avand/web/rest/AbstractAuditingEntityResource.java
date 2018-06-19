package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.AbstractAuditingEntity;

import io.avand.repository.AbstractAuditingEntityRepository;
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
 * REST controller for managing AbstractAuditingEntity.
 */
@RestController
@RequestMapping("/api")
public class AbstractAuditingEntityResource {

    private final Logger log = LoggerFactory.getLogger(AbstractAuditingEntityResource.class);

    private static final String ENTITY_NAME = "abstractAuditingEntity";

    private final AbstractAuditingEntityRepository abstractAuditingEntityRepository;

    public AbstractAuditingEntityResource(AbstractAuditingEntityRepository abstractAuditingEntityRepository) {
        this.abstractAuditingEntityRepository = abstractAuditingEntityRepository;
    }

    /**
     * POST  /abstract-auditing-entities : Create a new abstractAuditingEntity.
     *
     * @param abstractAuditingEntity the abstractAuditingEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new abstractAuditingEntity, or with status 400 (Bad Request) if the abstractAuditingEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/abstract-auditing-entities")
    @Timed
    public ResponseEntity<AbstractAuditingEntity> createAbstractAuditingEntity(@RequestBody AbstractAuditingEntity abstractAuditingEntity) throws URISyntaxException {
        log.debug("REST request to save AbstractAuditingEntity : {}", abstractAuditingEntity);
        if (abstractAuditingEntity.getId() != null) {
            throw new BadRequestAlertException("A new abstractAuditingEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbstractAuditingEntity result = abstractAuditingEntityRepository.save(abstractAuditingEntity);
        return ResponseEntity.created(new URI("/api/abstract-auditing-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /abstract-auditing-entities : Updates an existing abstractAuditingEntity.
     *
     * @param abstractAuditingEntity the abstractAuditingEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated abstractAuditingEntity,
     * or with status 400 (Bad Request) if the abstractAuditingEntity is not valid,
     * or with status 500 (Internal Server Error) if the abstractAuditingEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/abstract-auditing-entities")
    @Timed
    public ResponseEntity<AbstractAuditingEntity> updateAbstractAuditingEntity(@RequestBody AbstractAuditingEntity abstractAuditingEntity) throws URISyntaxException {
        log.debug("REST request to update AbstractAuditingEntity : {}", abstractAuditingEntity);
        if (abstractAuditingEntity.getId() == null) {
            return createAbstractAuditingEntity(abstractAuditingEntity);
        }
        AbstractAuditingEntity result = abstractAuditingEntityRepository.save(abstractAuditingEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, abstractAuditingEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /abstract-auditing-entities : get all the abstractAuditingEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of abstractAuditingEntities in body
     */
    @GetMapping("/abstract-auditing-entities")
    @Timed
    public List<AbstractAuditingEntity> getAllAbstractAuditingEntities() {
        log.debug("REST request to get all AbstractAuditingEntities");
        return abstractAuditingEntityRepository.findAll();
        }

    /**
     * GET  /abstract-auditing-entities/:id : get the "id" abstractAuditingEntity.
     *
     * @param id the id of the abstractAuditingEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the abstractAuditingEntity, or with status 404 (Not Found)
     */
    @GetMapping("/abstract-auditing-entities/{id}")
    @Timed
    public ResponseEntity<AbstractAuditingEntity> getAbstractAuditingEntity(@PathVariable Long id) {
        log.debug("REST request to get AbstractAuditingEntity : {}", id);
        AbstractAuditingEntity abstractAuditingEntity = abstractAuditingEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(abstractAuditingEntity));
    }

    /**
     * DELETE  /abstract-auditing-entities/:id : delete the "id" abstractAuditingEntity.
     *
     * @param id the id of the abstractAuditingEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/abstract-auditing-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAbstractAuditingEntity(@PathVariable Long id) {
        log.debug("REST request to delete AbstractAuditingEntity : {}", id);
        abstractAuditingEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
