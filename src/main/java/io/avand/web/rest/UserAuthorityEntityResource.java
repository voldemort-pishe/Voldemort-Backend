package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.UserAuthorityEntity;

import io.avand.repository.UserAuthorityEntityRepository;
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
 * REST controller for managing UserAuthorityEntity.
 */
@RestController
@RequestMapping("/api")
public class UserAuthorityEntityResource {

    private final Logger log = LoggerFactory.getLogger(UserAuthorityEntityResource.class);

    private static final String ENTITY_NAME = "userAuthorityEntity";

    private final UserAuthorityEntityRepository userAuthorityEntityRepository;

    public UserAuthorityEntityResource(UserAuthorityEntityRepository userAuthorityEntityRepository) {
        this.userAuthorityEntityRepository = userAuthorityEntityRepository;
    }

    /**
     * POST  /user-authority-entities : Create a new userAuthorityEntity.
     *
     * @param userAuthorityEntity the userAuthorityEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAuthorityEntity, or with status 400 (Bad Request) if the userAuthorityEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-authority-entities")
    @Timed
    public ResponseEntity<UserAuthorityEntity> createUserAuthorityEntity(@RequestBody UserAuthorityEntity userAuthorityEntity) throws URISyntaxException {
        log.debug("REST request to save UserAuthorityEntity : {}", userAuthorityEntity);
        if (userAuthorityEntity.getId() != null) {
            throw new BadRequestAlertException("A new userAuthorityEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAuthorityEntity result = userAuthorityEntityRepository.save(userAuthorityEntity);
        return ResponseEntity.created(new URI("/api/user-authority-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-authority-entities : Updates an existing userAuthorityEntity.
     *
     * @param userAuthorityEntity the userAuthorityEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAuthorityEntity,
     * or with status 400 (Bad Request) if the userAuthorityEntity is not valid,
     * or with status 500 (Internal Server Error) if the userAuthorityEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-authority-entities")
    @Timed
    public ResponseEntity<UserAuthorityEntity> updateUserAuthorityEntity(@RequestBody UserAuthorityEntity userAuthorityEntity) throws URISyntaxException {
        log.debug("REST request to update UserAuthorityEntity : {}", userAuthorityEntity);
        if (userAuthorityEntity.getId() == null) {
            return createUserAuthorityEntity(userAuthorityEntity);
        }
        UserAuthorityEntity result = userAuthorityEntityRepository.save(userAuthorityEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAuthorityEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-authority-entities : get all the userAuthorityEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userAuthorityEntities in body
     */
    @GetMapping("/user-authority-entities")
    @Timed
    public List<UserAuthorityEntity> getAllUserAuthorityEntities() {
        log.debug("REST request to get all UserAuthorityEntities");
        return userAuthorityEntityRepository.findAll();
        }

    /**
     * GET  /user-authority-entities/:id : get the "id" userAuthorityEntity.
     *
     * @param id the id of the userAuthorityEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAuthorityEntity, or with status 404 (Not Found)
     */
    @GetMapping("/user-authority-entities/{id}")
    @Timed
    public ResponseEntity<UserAuthorityEntity> getUserAuthorityEntity(@PathVariable Long id) {
        log.debug("REST request to get UserAuthorityEntity : {}", id);
        UserAuthorityEntity userAuthorityEntity = userAuthorityEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userAuthorityEntity));
    }

    /**
     * DELETE  /user-authority-entities/:id : delete the "id" userAuthorityEntity.
     *
     * @param id the id of the userAuthorityEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-authority-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserAuthorityEntity(@PathVariable Long id) {
        log.debug("REST request to delete UserAuthorityEntity : {}", id);
        userAuthorityEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
