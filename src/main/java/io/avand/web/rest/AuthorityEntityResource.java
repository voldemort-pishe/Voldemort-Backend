package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.AuthorityEntity;

import io.avand.repository.AuthorityEntityRepository;
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
 * REST controller for managing AuthorityEntity.
 */
@RestController
@RequestMapping("/api")
public class AuthorityEntityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityEntityResource.class);

    private static final String ENTITY_NAME = "authorityEntity";

    private final AuthorityEntityRepository authorityEntityRepository;

    public AuthorityEntityResource(AuthorityEntityRepository authorityEntityRepository) {
        this.authorityEntityRepository = authorityEntityRepository;
    }

    /**
     * POST  /authority-entities : Create a new authorityEntity.
     *
     * @param authorityEntity the authorityEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorityEntity, or with status 400 (Bad Request) if the authorityEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authority-entities")
    @Timed
    public ResponseEntity<AuthorityEntity> createAuthorityEntity(@RequestBody AuthorityEntity authorityEntity) throws URISyntaxException {
        log.debug("REST request to save AuthorityEntity : {}", authorityEntity);
        if (authorityEntity.getId() != null) {
            throw new BadRequestAlertException("A new authorityEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorityEntity result = authorityEntityRepository.save(authorityEntity);
        return ResponseEntity.created(new URI("/api/authority-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authority-entities : Updates an existing authorityEntity.
     *
     * @param authorityEntity the authorityEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorityEntity,
     * or with status 400 (Bad Request) if the authorityEntity is not valid,
     * or with status 500 (Internal Server Error) if the authorityEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authority-entities")
    @Timed
    public ResponseEntity<AuthorityEntity> updateAuthorityEntity(@RequestBody AuthorityEntity authorityEntity) throws URISyntaxException {
        log.debug("REST request to update AuthorityEntity : {}", authorityEntity);
        if (authorityEntity.getId() == null) {
            return createAuthorityEntity(authorityEntity);
        }
        AuthorityEntity result = authorityEntityRepository.save(authorityEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorityEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authority-entities : get all the authorityEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of authorityEntities in body
     */
    @GetMapping("/authority-entities")
    @Timed
    public List<AuthorityEntity> getAllAuthorityEntities() {
        log.debug("REST request to get all AuthorityEntities");
        return authorityEntityRepository.findAll();
        }

    /**
     * GET  /authority-entities/:id : get the "id" authorityEntity.
     *
     * @param id the id of the authorityEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorityEntity, or with status 404 (Not Found)
     */
    @GetMapping("/authority-entities/{id}")
    @Timed
    public ResponseEntity<AuthorityEntity> getAuthorityEntity(@PathVariable Long id) {
        log.debug("REST request to get AuthorityEntity : {}", id);
        AuthorityEntity authorityEntity = authorityEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authorityEntity));
    }

    /**
     * DELETE  /authority-entities/:id : delete the "id" authorityEntity.
     *
     * @param id the id of the authorityEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authority-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthorityEntity(@PathVariable Long id) {
        log.debug("REST request to delete AuthorityEntity : {}", id);
        authorityEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
