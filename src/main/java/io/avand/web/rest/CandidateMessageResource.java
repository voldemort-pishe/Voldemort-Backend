package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.CandidateMessageService;
import io.avand.service.dto.CandidateMessageDTO;
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

@RestController
@RequestMapping("/api/candidate-message")
public class CandidateMessageResource {

    private final String ENTITY_NAME = "CandidateMessage";

    private final Logger log = LoggerFactory.getLogger(CandidateMessageResource.class);
    private final CandidateMessageService candidateMessageService;

    public CandidateMessageResource(CandidateMessageService candidateMessageService) {
        this.candidateMessageService = candidateMessageService;
    }

    @PostMapping
    @Timed
    public ResponseEntity createCandidateMessage(@Valid @RequestBody CandidateMessageDTO candidateMessageDTO) throws URISyntaxException {
        log.debug("REST request to save candidateMessage : {}", candidateMessageDTO);
        if (candidateMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            CandidateMessageDTO result = candidateMessageService.save(candidateMessageDTO);
            return ResponseEntity.created(new URI("/api/candidate-message/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PutMapping
    @Timed
    public ResponseEntity updateCandidateMessage(@Valid @RequestBody CandidateMessageDTO candidateMessageDTO) throws URISyntaxException {
        log.debug("REST request to update candidateMessageDTO : {}", candidateMessageDTO);
        if (candidateMessageDTO.getId() == null) {
            return createCandidateMessage(candidateMessageDTO);
        }
        try {
            CandidateMessageDTO result = candidateMessageService.save(candidateMessageDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateMessageDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/candidate/{candidateId}")
    @Timed
    public ResponseEntity getAllCandidateByCandidateId(@PathVariable("candidateId") Long candidateId, @ApiParam Pageable page) {
        log.debug("REST request to get all CandidateMessageDtos by candidate id : {}", candidateId);
        Page<CandidateMessageDTO> candidateDTOS = candidateMessageService.findByCandidateId(candidateId,page);
        return new ResponseEntity<>(candidateDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Timed
    public ResponseEntity getCandidate(@PathVariable Long id) {
        log.debug("REST request to get CandidateDto : {}", id);
        try {
            CandidateMessageDTO candidateDTO = candidateMessageService.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateDTO));
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete CandidateDto : {}", id);
        try {
            candidateMessageService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
