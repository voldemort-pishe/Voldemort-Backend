package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.domain.enumeration.CandidateState;
import io.avand.service.CandidateService;
import io.avand.service.dto.CandidateDTO;
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
 * REST controller for managing CandidateEntity.
 */
@RestController
@RequestMapping("/api/candidate")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    private static final String ENTITY_NAME = "candidateEntity";

    private final CandidateService candidateService;

    public CandidateResource(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    /**
     * POST  /candidate : Create a new candidateEntity.
     *
     * @param candidateDTO the candidateEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateEntity, or with status 400 (Bad Request) if the candidateEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity createCandidate(@Valid @RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to save candidateDTO : {}", candidateDTO);
        if (candidateDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            candidateDTO.setState(CandidateState.PENDING);
            CandidateDTO result = candidateService.save(candidateDTO);
            return ResponseEntity.created(new URI("/api/candidate/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /candidate : Updates an existing candidateEntity.
     *
     * @param candidateDTO the candidateEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateEntity,
     * or with status 400 (Bad Request) if the candidateEntity is not valid,
     * or with status 500 (Internal Server Error) if the candidateEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity updateCandidate(@Valid @RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to update candidateDTO : {}", candidateDTO);
        if (candidateDTO.getId() == null) {
            return createCandidate(candidateDTO);
        }
        try {
            CandidateDTO result = candidateService.save(candidateDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /candidate : get all the candidateEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateEntities in body
     */
    @GetMapping
    @Timed
    public ResponseEntity getAllCandidate(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CandidateDtos");
        Page<CandidateDTO> candidateDTOS = candidateService.findAll(pageable);
        return new ResponseEntity<>(candidateDTOS, HttpStatus.OK);
    }

    /**
     * GET  /candidate/:id : get the "id" candidateEntity.
     *
     * @param id the id of the candidateEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateEntity, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity getCandidate(@PathVariable Long id) {
        log.debug("REST request to get CandidateDto : {}", id);
        try {
            CandidateDTO candidateDTO = candidateService.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateDTO));
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/job/{id}")
    @Timed
    public ResponseEntity getCandidateByJobId(@PathVariable("id") Long jobId,
                                              @ApiParam Pageable pageable){
        log.debug("REST Request to get Candidates by job id : {}");
        try{
            Page<CandidateDTO> candidateDTOS = candidateService.findByJobId(jobId,pageable);
            return new ResponseEntity<>(candidateDTOS,HttpStatus.OK);
        }catch (NotFoundException e){
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/company")
    @Timed
    public ResponseEntity getCandidateByCompany(@RequestAttribute("companyId") Long companyId,
                                              @ApiParam Pageable pageable){
        log.debug("REST Request to get Candidates by job id : {}");
        try{
            Page<CandidateDTO> candidateDTOS = candidateService.findByCompanyId(companyId,pageable);
            return new ResponseEntity<>(candidateDTOS,HttpStatus.OK);
        }catch (NotFoundException e){
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /candidate/:id : delete the "id" candidateEntity.
     *
     * @param id the id of the candidateEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete CandidateDto : {}", id);
        candidateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
