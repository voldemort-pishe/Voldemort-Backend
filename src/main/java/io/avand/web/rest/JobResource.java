package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.JobEntity;

import io.avand.repository.JobRepository;
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

/**
 * REST controller for managing JobEntity.
 */
@RestController
@RequestMapping("/api")
public class JobResource {

    private final Logger log = LoggerFactory.getLogger(JobResource.class);

    private static final String ENTITY_NAME = "jobEntity";

    private final JobRepository jobRepository;

    public JobResource(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * POST  /job-entities : Create a new jobEntity.
     *
     * @param jobEntity the jobEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobEntity, or with status 400 (Bad Request) if the jobEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-entities")
    @Timed
    public ResponseEntity<JobEntity> createJobEntity(@Valid @RequestBody JobEntity jobEntity) throws URISyntaxException {
        log.debug("REST request to save JobEntity : {}", jobEntity);
        if (jobEntity.getId() != null) {
            throw new BadRequestAlertException("A new jobEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobEntity result = jobRepository.save(jobEntity);
        return ResponseEntity.created(new URI("/api/job-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-entities : Updates an existing jobEntity.
     *
     * @param jobEntity the jobEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobEntity,
     * or with status 400 (Bad Request) if the jobEntity is not valid,
     * or with status 500 (Internal Server Error) if the jobEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-entities")
    @Timed
    public ResponseEntity<JobEntity> updateJobEntity(@Valid @RequestBody JobEntity jobEntity) throws URISyntaxException {
        log.debug("REST request to update JobEntity : {}", jobEntity);
        if (jobEntity.getId() == null) {
            return createJobEntity(jobEntity);
        }
        JobEntity result = jobRepository.save(jobEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-entities : get all the jobEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobEntities in body
     */
    @GetMapping("/job-entities")
    @Timed
    public List<JobEntity> getAllJobEntities() {
        log.debug("REST request to get all JobEntities");
        return jobRepository.findAll();
        }

    /**
     * GET  /job-entities/:id : get the "id" jobEntity.
     *
     * @param id the id of the jobEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobEntity, or with status 404 (Not Found)
     */
    @GetMapping("/job-entities/{id}")
    @Timed
    public ResponseEntity<JobEntity> getJobEntity(@PathVariable Long id) {
        log.debug("REST request to get JobEntity : {}", id);
        JobEntity jobEntity = jobRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobEntity));
    }

    /**
     * DELETE  /job-entities/:id : delete the "id" jobEntity.
     *
     * @param id the id of the jobEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobEntity(@PathVariable Long id) {
        log.debug("REST request to delete JobEntity : {}", id);
        jobRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
