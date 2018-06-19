package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.UserPermissionEntity;

import io.avand.repository.UserPermissionEntityRepository;
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
 * REST controller for managing UserPermissionEntity.
 */
@RestController
@RequestMapping("/api")
public class UserPermissionEntityResource {

    private final Logger log = LoggerFactory.getLogger(UserPermissionEntityResource.class);

    private static final String ENTITY_NAME = "userPermissionEntity";

    private final UserPermissionEntityRepository userPermissionEntityRepository;

    public UserPermissionEntityResource(UserPermissionEntityRepository userPermissionEntityRepository) {
        this.userPermissionEntityRepository = userPermissionEntityRepository;
    }

    /**
     * POST  /user-permission-entities : Create a new userPermissionEntity.
     *
     * @param userPermissionEntity the userPermissionEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPermissionEntity, or with status 400 (Bad Request) if the userPermissionEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-permission-entities")
    @Timed
    public ResponseEntity<UserPermissionEntity> createUserPermissionEntity(@RequestBody UserPermissionEntity userPermissionEntity) throws URISyntaxException {
        log.debug("REST request to save UserPermissionEntity : {}", userPermissionEntity);
        if (userPermissionEntity.getId() != null) {
            throw new BadRequestAlertException("A new userPermissionEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPermissionEntity result = userPermissionEntityRepository.save(userPermissionEntity);
        return ResponseEntity.created(new URI("/api/user-permission-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-permission-entities : Updates an existing userPermissionEntity.
     *
     * @param userPermissionEntity the userPermissionEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPermissionEntity,
     * or with status 400 (Bad Request) if the userPermissionEntity is not valid,
     * or with status 500 (Internal Server Error) if the userPermissionEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-permission-entities")
    @Timed
    public ResponseEntity<UserPermissionEntity> updateUserPermissionEntity(@RequestBody UserPermissionEntity userPermissionEntity) throws URISyntaxException {
        log.debug("REST request to update UserPermissionEntity : {}", userPermissionEntity);
        if (userPermissionEntity.getId() == null) {
            return createUserPermissionEntity(userPermissionEntity);
        }
        UserPermissionEntity result = userPermissionEntityRepository.save(userPermissionEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPermissionEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-permission-entities : get all the userPermissionEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userPermissionEntities in body
     */
    @GetMapping("/user-permission-entities")
    @Timed
    public List<UserPermissionEntity> getAllUserPermissionEntities() {
        log.debug("REST request to get all UserPermissionEntities");
        return userPermissionEntityRepository.findAll();
        }

    /**
     * GET  /user-permission-entities/:id : get the "id" userPermissionEntity.
     *
     * @param id the id of the userPermissionEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPermissionEntity, or with status 404 (Not Found)
     */
    @GetMapping("/user-permission-entities/{id}")
    @Timed
    public ResponseEntity<UserPermissionEntity> getUserPermissionEntity(@PathVariable Long id) {
        log.debug("REST request to get UserPermissionEntity : {}", id);
        UserPermissionEntity userPermissionEntity = userPermissionEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPermissionEntity));
    }

    /**
     * DELETE  /user-permission-entities/:id : delete the "id" userPermissionEntity.
     *
     * @param id the id of the userPermissionEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-permission-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPermissionEntity(@PathVariable Long id) {
        log.debug("REST request to delete UserPermissionEntity : {}", id);
        userPermissionEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
