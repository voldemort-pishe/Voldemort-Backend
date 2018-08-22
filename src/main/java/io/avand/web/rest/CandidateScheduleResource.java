package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.security.AuthoritiesConstants;
import io.avand.service.CandidateScheduleService;
import io.avand.service.CandidateService;
import io.avand.service.UserService;
import io.avand.service.dto.CandidateScheduleDTO;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.component.CandidateScheduleComponent;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.avand.web.rest.vm.CandidateScheduleOwnerDateVM;
import io.avand.web.rest.vm.CandidateScheduleVm;
import io.avand.web.rest.vm.response.ResponseVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CandidateScheduleDTO.
 */
@RestController
@RequestMapping("/api/candidate-schedule")
@Secured(AuthoritiesConstants.SUBSCRIPTION)
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
     * @param CandidateScheduleDTO the CandidateScheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new CandidateScheduleDTO, or with status 400 (Bad Request) if the CandidateScheduleDTO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity<ResponseVM<CandidateScheduleDTO>> create(@RequestBody CandidateScheduleDTO CandidateScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save CandidateScheduleDTO : {}", CandidateScheduleDTO);
        if (CandidateScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new CandidateScheduleDTO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            ResponseVM<CandidateScheduleDTO> result = candidateScheduleComponent.save(CandidateScheduleDTO);
            return ResponseEntity.created(new URI("/api/candidate-schedule-entities/" + result.getData().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
                .body(result);
        } catch (NotFoundException | ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /candidate-schedule-entities : Updates an existing CandidateScheduleDTO.
     *
     * @param CandidateScheduleDTO the CandidateScheduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated CandidateScheduleDTO,
     * or with status 400 (Bad Request) if the CandidateScheduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the CandidateScheduleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity<ResponseVM<CandidateScheduleDTO>> update(@RequestBody CandidateScheduleDTO CandidateScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update CandidateScheduleDTO : {}", CandidateScheduleDTO);
        if (CandidateScheduleDTO.getId() == null) {
            return create(CandidateScheduleDTO);
        }
        try {
            ResponseVM<CandidateScheduleDTO> result = candidateScheduleComponent.save(CandidateScheduleDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, CandidateScheduleDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException | SecurityException e) {
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
    public ResponseEntity<ResponseVM<CandidateScheduleDTO>> getById(@PathVariable Long id) {
        log.debug("REST request to get CandidateScheduleDTO : {}", id);
        try {
            ResponseVM<CandidateScheduleDTO> CandidateScheduleDTO = candidateScheduleComponent.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(CandidateScheduleDTO));
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/owner")
    @Timed
    public ResponseEntity<Page<ResponseVM<CandidateScheduleDTO>>> getByOwner(@ApiParam Pageable pageable) {
        log.debug("REST Request to get CandidateSchedule by owner");
        try {
            Page<ResponseVM<CandidateScheduleDTO>> candidateScheduleDTOS = candidateScheduleComponent.findByOwner(pageable);
            return new ResponseEntity<>(candidateScheduleDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/owner")
    @Timed
    public ResponseEntity<Page<ResponseVM<CandidateScheduleDTO>>> getByOwnerTimed(@ApiParam Pageable pageable, @RequestBody CandidateScheduleOwnerDateVM ownerDateVM) {
        log.debug("REST Request to get CandidateSchedule by date : {}", ownerDateVM);
        try {
            Page<ResponseVM<CandidateScheduleDTO>> candidateScheduleDTOS = candidateScheduleComponent.findByOwnerAndDate(ownerDateVM.getStartDate(), ownerDateVM.getEndDate(), pageable);
            return new ResponseEntity<>(candidateScheduleDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/candidate/{id}")
    @Timed
    public ResponseEntity<Page<ResponseVM<CandidateScheduleDTO>>> getByCandidateId(@ApiParam Pageable pageable, @PathVariable("id") Long candidateId) {
        log.debug("REST Request to get by candidateId : {}", candidateId);
        try {
            Page<ResponseVM<CandidateScheduleDTO>> candidateScheduleDTOS = candidateScheduleComponent.findByCandidate(candidateId, pageable);
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
