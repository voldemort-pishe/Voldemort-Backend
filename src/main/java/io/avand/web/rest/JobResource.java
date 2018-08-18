package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.service.JobService;
import io.avand.service.dto.JobDTO;
import io.avand.service.util.RandomUtil;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/job")
public class JobResource {

    private final Logger log = LoggerFactory.getLogger(JobResource.class);

    private static final String ENTITY_NAME = "jobEntity";

    private final JobService jobService;

    public JobResource(JobService jobService) {
        this.jobService = jobService;
    }


    /**
     * POST  /job : Create a new jobEntity.
     *
     * @param jobDTO the jobEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobEntity, or with status 400 (Bad Request) if the jobEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobDTO jobDTO,
                                            @RequestAttribute("companyId") Long companyId) throws URISyntaxException {
        log.debug("REST request to save Job : {}", jobDTO);
        if (jobDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            jobDTO.setCompanyId(companyId);
            jobDTO.setUniqueId(RandomUtil.getUniqueId());
            JobDTO result = jobService.save(jobDTO);
            return ResponseEntity.created(new URI("/api/job/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /job : Updates an existing jobEntity.
     *
     * @param jobDTO the jobEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobEntity,
     * or with status 400 (Bad Request) if the jobEntity is not valid,
     * or with status 500 (Internal Server Error) if the jobEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity<JobDTO> updateJob(@Valid @RequestBody JobDTO jobDTO,
                                            @RequestAttribute("companyId") Long companyId) throws URISyntaxException {
        log.debug("REST request to update Job : {}", jobDTO);
        if (jobDTO.getId() == null) {
            return createJob(jobDTO, companyId);
        }
        try {
            jobDTO.setCompanyId(companyId);
            JobDTO result = jobService.save(jobDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /job : get all the jobEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobEntities in body
     */
    @GetMapping
    @Timed
    public ResponseEntity<Page<JobDTO>> getAllJob(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Job");
        try {
            Page<JobDTO> jobDTOS = jobService.findAll(pageable);
            return new ResponseEntity<>(jobDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /job/:id : get the "id" jobEntity.
     *
     * @param id the id of the jobEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobEntity, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id) {
        log.debug("REST request to get Job : {}", id);
        try {
            JobDTO jobDTO = jobService.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobDTO));
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /job/:id : delete the "id" jobEntity.
     *
     * @param id the id of the jobEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.debug("REST request to delete Job : {}", id);
        try {
            jobService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }


    /**
     * GET  /job/company-list/{id} : get all the jobEntities by company id.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobEntities in body
     */
    @GetMapping("/company-list")
    @Timed
    public ResponseEntity<Page<JobDTO>> getAllJobByCompany(@ApiParam Pageable pageable, @RequestAttribute("companyId") Long companyId) {
        log.debug("REST request to get all Job");
        try {
            Page<JobDTO> jobDTOS = jobService.findAllByCompanyId(pageable, companyId);
            return new ResponseEntity<>(jobDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

}
