package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.UserEntity;

import io.avand.repository.UserEntityRepository;
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
 * REST controller for managing UserEntity.
 */
@RestController
@RequestMapping("/api")
public class UserEntityResource {

    private final Logger log = LoggerFactory.getLogger(UserEntityResource.class);

    private static final String ENTITY_NAME = "userEntity";

    private final UserEntityRepository userEntityRepository;

    public UserEntityResource(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * POST  /user-entities : Create a new userEntity.
     *
     * @param userEntity the userEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userEntity, or with status 400 (Bad Request) if the userEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-entities")
    @Timed
    public ResponseEntity<UserEntity> createUserEntity(@RequestBody UserEntity userEntity) throws URISyntaxException {
        log.debug("REST request to save UserEntity : {}", userEntity);
        if (userEntity.getId() != null) {
            throw new BadRequestAlertException("A new userEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserEntity result = userEntityRepository.save(userEntity);
        return ResponseEntity.created(new URI("/api/user-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-entities : Updates an existing userEntity.
     *
     * @param userEntity the userEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userEntity,
     * or with status 400 (Bad Request) if the userEntity is not valid,
     * or with status 500 (Internal Server Error) if the userEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-entities")
    @Timed
    public ResponseEntity<UserEntity> updateUserEntity(@RequestBody UserEntity userEntity) throws URISyntaxException {
        log.debug("REST request to update UserEntity : {}", userEntity);
        if (userEntity.getId() == null) {
            return createUserEntity(userEntity);
        }
        UserEntity result = userEntityRepository.save(userEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-entities : get all the userEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userEntities in body
     */
    @GetMapping("/user-entities")
    @Timed
    public List<UserEntity> getAllUserEntities() {
        log.debug("REST request to get all UserEntities");
        return userEntityRepository.findAll();
        }

    /**
     * GET  /user-entities/:id : get the "id" userEntity.
     *
     * @param id the id of the userEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userEntity, or with status 404 (Not Found)
     */
    @GetMapping("/user-entities/{id}")
    @Timed
    public ResponseEntity<UserEntity> getUserEntity(@PathVariable Long id) {
        log.debug("REST request to get UserEntity : {}", id);
        UserEntity userEntity = userEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userEntity));
    }

    /**
     * DELETE  /user-entities/:id : delete the "id" userEntity.
     *
     * @param id the id of the userEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserEntity(@PathVariable Long id) {
        log.debug("REST request to delete UserEntity : {}", id);
        userEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
