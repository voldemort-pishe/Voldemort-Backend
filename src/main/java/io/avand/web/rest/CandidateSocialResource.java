package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.CandidateSocialService;
import io.avand.service.dto.CandidateSocialDTO;
import io.avand.web.rest.component.CandidateSocialComponent;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/candidate-social")
public class CandidateSocialResource {
    private final Logger log = LoggerFactory.getLogger(CandidateSocialResource.class);
    private final CandidateSocialComponent candidateSocialComponent;
    private final CandidateSocialService candidateSocialService;

    public CandidateSocialResource(CandidateSocialComponent candidateSocialComponent,
                                   CandidateSocialService candidateSocialService) {
        this.candidateSocialComponent = candidateSocialComponent;
        this.candidateSocialService = candidateSocialService;
    }

    @PostMapping
    @Timed
    @PreAuthorize("isMember(#candidateSocialDTO.candidateId,'CANDIDATE','EDIT_CANDIDATE')")
    public ResponseEntity<ResponseVM<CandidateSocialDTO>> save(@RequestBody @Valid CandidateSocialDTO candidateSocialDTO) {
        log.debug("REST Request to save candidateSocial : {}", candidateSocialDTO);
        try {
            ResponseVM<CandidateSocialDTO> responseVM = candidateSocialComponent.save(candidateSocialDTO);
            return new ResponseEntity<>(responseVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PutMapping
    @Timed
    @PreAuthorize("isMember(#candidateSocialDTO.candidateId,'CANDIDATE','EDIT_CANDIDATE')")
    public ResponseEntity<ResponseVM<CandidateSocialDTO>> update(@RequestBody @Valid CandidateSocialDTO candidateSocialDTO) {
        log.debug("REST Request to update candidateSocial : {}", candidateSocialDTO);
        if (candidateSocialDTO.getId() == null) {
            return save(candidateSocialDTO);
        }
        try {
            ResponseVM<CandidateSocialDTO> responseVM = candidateSocialComponent.save(candidateSocialDTO);
            return new ResponseEntity<>(responseVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'CANDIDATE_SOCIAL','EDIT_CANDIDATE')")
    public ResponseEntity<ResponseVM<CandidateSocialDTO>> findById(@PathVariable("id") Long id) {
        log.debug("REST Request to find candidateSocial by id : {}", id);
        try {
            ResponseVM<CandidateSocialDTO> responseVM = candidateSocialComponent.findById(id);
            return new ResponseEntity<>(responseVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/candidate/{id}")
    @Timed
    @PreAuthorize("isMember(#candidateId,'CANDIDATE','EDIT_CANDIDATE')")
    public ResponseEntity<List<ResponseVM<CandidateSocialDTO>>> findAll(@PathVariable("id") Long candidateId) {
        log.debug("REST Request to findAll candidateSocial by candidateId : {}", candidateId);
        try {
            List<ResponseVM<CandidateSocialDTO>> responseVMS = candidateSocialComponent.findAllByCandidateId(candidateId);
            return new ResponseEntity<>(responseVMS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isMember(#id,'CANDIDATE_SOCIAL','EDIT_CANDIDATE')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        log.debug("REST Request to delete candidateSocial : {}", id);
        try {
            candidateSocialService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
