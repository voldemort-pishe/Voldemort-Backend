package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.TalentPoolEntity;

import io.avand.repository.TalentPoolRepository;
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
 * REST controller for managing TalentPoolEntity.
 */
@RestController
@RequestMapping("/api")
public class TalentPoolResource {

    private final Logger log = LoggerFactory.getLogger(TalentPoolResource.class);

    private static final String ENTITY_NAME = "talentPoolEntity";

    private final TalentPoolRepository talentPoolRepository;

    public TalentPoolResource(TalentPoolRepository talentPoolRepository) {
        this.talentPoolRepository = talentPoolRepository;
    }

    /**
     * POST  /talent-pool-entities : Create a new talentPoolEntity.
     *
     * @param talentPoolEntity the talentPoolEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new talentPoolEntity, or with status 400 (Bad Request) if the talentPoolEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/talent-pool-entities")
    @Timed
    public ResponseEntity<TalentPoolEntity> createTalentPoolEntity(@RequestBody TalentPoolEntity talentPoolEntity) throws URISyntaxException {
        log.debug("REST request to save TalentPoolEntity : {}", talentPoolEntity);
        if (talentPoolEntity.getId() != null) {
            throw new BadRequestAlertException("A new talentPoolEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalentPoolEntity result = talentPoolRepository.save(talentPoolEntity);
        return ResponseEntity.created(new URI("/api/talent-pool-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /talent-pool-entities : Updates an existing talentPoolEntity.
     *
     * @param talentPoolEntity the talentPoolEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated talentPoolEntity,
     * or with status 400 (Bad Request) if the talentPoolEntity is not valid,
     * or with status 500 (Internal Server Error) if the talentPoolEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/talent-pool-entities")
    @Timed
    public ResponseEntity<TalentPoolEntity> updateTalentPoolEntity(@RequestBody TalentPoolEntity talentPoolEntity) throws URISyntaxException {
        log.debug("REST request to update TalentPoolEntity : {}", talentPoolEntity);
        if (talentPoolEntity.getId() == null) {
            return createTalentPoolEntity(talentPoolEntity);
        }
        TalentPoolEntity result = talentPoolRepository.save(talentPoolEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, talentPoolEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /talent-pool-entities : get all the talentPoolEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of talentPoolEntities in body
     */
    @GetMapping("/talent-pool-entities")
    @Timed
    public List<TalentPoolEntity> getAllTalentPoolEntities() {
        log.debug("REST request to get all TalentPoolEntities");
        return talentPoolRepository.findAll();
        }

    /**
     * GET  /talent-pool-entities/:id : get the "id" talentPoolEntity.
     *
     * @param id the id of the talentPoolEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the talentPoolEntity, or with status 404 (Not Found)
     */
    @GetMapping("/talent-pool-entities/{id}")
    @Timed
    public ResponseEntity<TalentPoolEntity> getTalentPoolEntity(@PathVariable Long id) {
        log.debug("REST request to get TalentPoolEntity : {}", id);
        TalentPoolEntity talentPoolEntity = talentPoolRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(talentPoolEntity));
    }

    /**
     * DELETE  /talent-pool-entities/:id : delete the "id" talentPoolEntity.
     *
     * @param id the id of the talentPoolEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/talent-pool-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteTalentPoolEntity(@PathVariable Long id) {
        log.debug("REST request to delete TalentPoolEntity : {}", id);
        talentPoolRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
