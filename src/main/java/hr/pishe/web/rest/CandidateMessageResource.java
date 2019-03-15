package hr.pishe.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.pishe.service.CandidateMessageService;
import hr.pishe.service.dto.CandidateMessageDTO;
import hr.pishe.web.rest.component.CandidateMessageComponent;
import hr.pishe.web.rest.errors.BadRequestAlertException;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.util.HeaderUtil;
import hr.pishe.web.rest.vm.CandidateMessageVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidate-message")
public class CandidateMessageResource {

    private final String ENTITY_NAME = "CandidateMessage";

    private final Logger log = LoggerFactory.getLogger(CandidateMessageResource.class);
    private final CandidateMessageService candidateMessageService;
    private final CandidateMessageComponent candidateMessageComponent;


    public CandidateMessageResource(CandidateMessageService candidateMessageService,
                                    CandidateMessageComponent candidateMessageComponent) {
        this.candidateMessageService = candidateMessageService;
        this.candidateMessageComponent = candidateMessageComponent;
    }

    @PostMapping
    @Timed
    @PreAuthorize("isMember(#candidateMessageDTO.candidateId,'CANDIDATE','ADD_CANDIDATE_MESSAGE')")
    public ResponseEntity<ResponseVM<CandidateMessageDTO>> createCandidateMessage
        (@Valid @RequestBody CandidateMessageDTO candidateMessageDTO)
        throws URISyntaxException {
        log.debug("REST request to save candidateMessage : {}", candidateMessageDTO);
        if (candidateMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            ResponseVM<CandidateMessageDTO> result = candidateMessageComponent.save(candidateMessageDTO);
            return ResponseEntity.created(new URI("/api/candidate-message/" + result.getData().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/create-specific")
    @Timed
    @PreAuthorize("isMember(#candidateMessageVM.candidateId,'CANDIDATE','ADD_CANDIDATE_MESSAGE')")
    public ResponseEntity<ResponseVM<CandidateMessageDTO>> createCandidateSpecificMessage(
        @RequestBody CandidateMessageVM candidateMessageVM
    ) throws URISyntaxException {
        log.debug("REST Request to save candidateMessage : {}", candidateMessageVM);
        try {
            ResponseVM<CandidateMessageDTO> result = candidateMessageComponent
                .save(
                    candidateMessageVM.getSubject(),
                    candidateMessageVM.getMessage(),
                    candidateMessageVM.getParent(),
                    candidateMessageVM.getCandidateId()
                );
            return ResponseEntity.created(new URI("/api/candidate-message/" + result.getData().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/candidate/{candidateId}")
    @Timed
    @PreAuthorize("isMember(#candidateId,'CANDIDATE','VIEW_CANDIDATE_MESSAGE')")
    public ResponseEntity<Page<ResponseVM<CandidateMessageDTO>>> getAllCandidateByCandidateId
        (@PathVariable("candidateId") Long candidateId, @ApiParam Pageable page) {
        log.debug("REST request to get all CandidateMessageDTOs by candidate id : {}", candidateId);
        try {
            Page<ResponseVM<CandidateMessageDTO>> candidateDTOS =
                candidateMessageComponent.findByCandidateId(candidateId, page);
            return new ResponseEntity<>(candidateDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'CANDIDATE','VIEW_CANDIDATE_MESSAGE')")
    public ResponseEntity<ResponseVM<CandidateMessageDTO>> getCandidate(@PathVariable Long id) {
        log.debug("REST request to get CandidateDto : {}", id);
        try {
            ResponseVM<CandidateMessageDTO> candidateDTO = candidateMessageComponent.findById(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateDTO));
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

}
