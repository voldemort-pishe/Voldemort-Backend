package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.FileEntity;

import io.avand.repository.FileEntityRepository;
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
 * REST controller for managing FileEntity.
 */
@RestController
@RequestMapping("/api")
public class FileEntityResource {

    private final Logger log = LoggerFactory.getLogger(FileEntityResource.class);

    private static final String ENTITY_NAME = "fileEntity";

    private final FileEntityRepository fileEntityRepository;

    public FileEntityResource(FileEntityRepository fileEntityRepository) {
        this.fileEntityRepository = fileEntityRepository;
    }

    /**
     * POST  /file-entities : Create a new fileEntity.
     *
     * @param fileEntity the fileEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileEntity, or with status 400 (Bad Request) if the fileEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-entities")
    @Timed
    public ResponseEntity<FileEntity> createFileEntity(@Valid @RequestBody FileEntity fileEntity) throws URISyntaxException {
        log.debug("REST request to save FileEntity : {}", fileEntity);
        if (fileEntity.getId() != null) {
            throw new BadRequestAlertException("A new fileEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileEntity result = fileEntityRepository.save(fileEntity);
        return ResponseEntity.created(new URI("/api/file-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-entities : Updates an existing fileEntity.
     *
     * @param fileEntity the fileEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileEntity,
     * or with status 400 (Bad Request) if the fileEntity is not valid,
     * or with status 500 (Internal Server Error) if the fileEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-entities")
    @Timed
    public ResponseEntity<FileEntity> updateFileEntity(@Valid @RequestBody FileEntity fileEntity) throws URISyntaxException {
        log.debug("REST request to update FileEntity : {}", fileEntity);
        if (fileEntity.getId() == null) {
            return createFileEntity(fileEntity);
        }
        FileEntity result = fileEntityRepository.save(fileEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-entities : get all the fileEntities.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of fileEntities in body
     */
    @GetMapping("/file-entities")
    @Timed
    public List<FileEntity> getAllFileEntities(@RequestParam(required = false) String filter) {
        if ("candidate-is-null".equals(filter)) {
            log.debug("REST request to get all FileEntitys where candidate is null");
            return StreamSupport
                .stream(fileEntityRepository.findAll().spliterator(), false)
                .filter(fileEntity -> fileEntity.getCandidate() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all FileEntities");
        return fileEntityRepository.findAll();
        }

    /**
     * GET  /file-entities/:id : get the "id" fileEntity.
     *
     * @param id the id of the fileEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileEntity, or with status 404 (Not Found)
     */
    @GetMapping("/file-entities/{id}")
    @Timed
    public ResponseEntity<FileEntity> getFileEntity(@PathVariable Long id) {
        log.debug("REST request to get FileEntity : {}", id);
        FileEntity fileEntity = fileEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileEntity));
    }

    /**
     * DELETE  /file-entities/:id : delete the "id" fileEntity.
     *
     * @param id the id of the fileEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileEntity(@PathVariable Long id) {
        log.debug("REST request to delete FileEntity : {}", id);
        fileEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
