package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.domain.enumeration.ScheduleStatus;
import io.avand.security.AuthoritiesConstants;
import io.avand.service.CandidateScheduleService;
import io.avand.service.dto.CandidateScheduleDTO;
import io.avand.web.rest.component.CandidateScheduleComponent;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.avand.web.rest.vm.CandidateScheduleDateVM;
import io.avand.web.rest.vm.response.ResponseVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Optional;

/**
 * REST controller for managing CandidateScheduleDTO.
 */
@RestController
@RequestMapping("/api/candidate-schedule")
public class CandidateScheduleResource {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleResource.class);

    private static final String ENTITY_NAME = "CandidateScheduleDTO";

    private final CandidateScheduleService candidateScheduleService;
    private final CandidateScheduleComponent candidateScheduleComponent;

    public CandidateScheduleResource(CandidateScheduleService candidateScheduleService,
                                     CandidateScheduleComponent candidateScheduleComponent) {
        this.candidateScheduleService = candidateScheduleService;
        this.candidateScheduleComponent = candidateScheduleComponent;
    }

    /**
     * POST  /candidate-schedule : Create a new candidateSchedule.
     *
     * @param candidateScheduleDTO the CandidateScheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new CandidateScheduleDTO, or with status 400 (Bad Request) if the CandidateScheduleDTO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    @PreAuthorize("isMember(#candidateScheduleDTO.candidateId,'CANDIDATE','ADD_SCHEDULE')")
    public ResponseEntity<ResponseVM<CandidateScheduleDTO>> create
    (@RequestBody CandidateScheduleDTO candidateScheduleDTO)
        throws URISyntaxException {
        log.debug("REST request to save CandidateScheduleDTO : {}", candidateScheduleDTO);
        if (candidateScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new CandidateScheduleDTO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            candidateScheduleDTO.setStatus(ScheduleStatus.SCHEDULED);
            ResponseVM<CandidateScheduleDTO> result = candidateScheduleComponent.save(candidateScheduleDTO);
            return ResponseEntity.created(new URI("/api/candidate-schedule-entities/" + result.getData().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
                .body(result);
        } catch (NotFoundException | IOException | ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /candidate-schedule-entities : Updates an existing CandidateScheduleDTO.
     *
     * @param candidateScheduleDTO the CandidateScheduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated CandidateScheduleDTO,
     * or with status 400 (Bad Request) if the CandidateScheduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the CandidateScheduleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    @PreAuthorize("isMember(#candidateScheduleDTO.candidateId,'CANDIDATE','EDIT_SCHEDULE')")
    public ResponseEntity<ResponseVM<CandidateScheduleDTO>> update
    (@RequestBody CandidateScheduleDTO candidateScheduleDTO)
        throws URISyntaxException {
        log.debug("REST request to update CandidateScheduleDTO : {}", candidateScheduleDTO);
        if (candidateScheduleDTO.getId() == null) {
            return create(candidateScheduleDTO);
        }
        try {
            ResponseVM<CandidateScheduleDTO> result = candidateScheduleComponent.save(candidateScheduleDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateScheduleDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException | IOException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /candidate-schedule-entities/:id : get the "id" CandidateScheduleDTO.
     *
     * @param id the id of the CandidateScheduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the CandidateScheduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'SCHEDULE','VIEW_SCHEDULE')")
    public ResponseEntity<ResponseVM<CandidateScheduleDTO>> getById(@PathVariable Long id) {
        log.debug("REST request to get CandidateScheduleDTO : {}", id);
        try {
            ResponseVM<CandidateScheduleDTO> candidateScheduleDTO = candidateScheduleComponent.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateScheduleDTO));
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping
    @Timed
    @PreAuthorize("isMember('VIEW_SCHEDULE')")
    public ResponseEntity<Page<ResponseVM<CandidateScheduleDTO>>> getAll(@ApiParam Pageable pageable) {
        log.debug("REST Request to get CandidateSchedules");
        try {
            Page<ResponseVM<CandidateScheduleDTO>> candidateScheduleDTOS =
                candidateScheduleComponent.findAll(pageable);
            return new ResponseEntity<>(candidateScheduleDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/time")
    @Timed
    @PreAuthorize("isMember('VIEW_SCHEDULE')")
    public ResponseEntity<Page<ResponseVM<CandidateScheduleDTO>>> getByTime
        (@ApiParam Pageable pageable, @RequestBody CandidateScheduleDateVM dateVM) {
        log.debug("REST Request to get CandidateSchedules by date : {}", dateVM);
        try {
            Page<ResponseVM<CandidateScheduleDTO>> candidateScheduleDTOS =
                candidateScheduleComponent.findByDate(dateVM.getStartDate(), dateVM.getEndDate(), pageable);
            return new ResponseEntity<>(candidateScheduleDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/candidate/{id}")
    @Timed
    @PreAuthorize("isMember(#candidateId,'CANDIDATE','VIEW_SCHEDULE')")
    public ResponseEntity<Page<ResponseVM<CandidateScheduleDTO>>> getByCandidateId
        (@ApiParam Pageable pageable, @PathVariable("id") Long candidateId) {
        log.debug("REST Request to get by candidateId : {}", candidateId);
        try {
            Page<ResponseVM<CandidateScheduleDTO>> candidateScheduleDTOS =
                candidateScheduleComponent.findByCandidate(candidateId, pageable);
            return new ResponseEntity<>(candidateScheduleDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /candidate-schedule-entities/:id : delete the "id" CandidateScheduleDTO.
     *
     * @param id the id of the CandidateScheduleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'SCHEDULE','DELETE_SCHEDULE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete CandidateScheduleDTO : {}", id);
        try {
            candidateScheduleService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
