package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.CandidateScheduleMemberService;
import io.avand.service.dto.CandidateScheduleMemberDTO;
import io.avand.web.rest.component.CandidateScheduleMemberComponent;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-schedule-member")
public class CandidateScheduleMemberResource {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleMemberResource.class);
    private final CandidateScheduleMemberComponent candidateScheduleMemberComponent;
    private final CandidateScheduleMemberService candidateScheduleMemberService;

    public CandidateScheduleMemberResource(CandidateScheduleMemberComponent candidateScheduleMemberComponent,
                                           CandidateScheduleMemberService candidateScheduleMemberService) {
        this.candidateScheduleMemberComponent = candidateScheduleMemberComponent;
        this.candidateScheduleMemberService = candidateScheduleMemberService;
    }

    @PostMapping
    @Timed
    @PreAuthorize("isMember(#candidateScheduleMemberDTO.candidateScheduleId,'SCHEDULE','ADD_SCHEDULE_MEMBER')")
    public ResponseEntity<ResponseVM<CandidateScheduleMemberDTO>> save(
        @RequestBody CandidateScheduleMemberDTO candidateScheduleMemberDTO) {
        log.debug("REST Request to save candidateScheduleMember : {}", candidateScheduleMemberDTO);
        try {
            ResponseVM<CandidateScheduleMemberDTO> responseVM =
                candidateScheduleMemberComponent.save(candidateScheduleMemberDTO);
            return new ResponseEntity<>(responseVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PutMapping
    @Timed
    @PreAuthorize("isMember(#candidateScheduleMemberDTO.candidateScheduleId,'SCHEDULE','EDIT_SCHEDULE_MEMBER')")
    public ResponseEntity<ResponseVM<CandidateScheduleMemberDTO>> update(
        @RequestBody CandidateScheduleMemberDTO candidateScheduleMemberDTO) {
        log.debug("REST Request to save candidateScheduleMember : {}", candidateScheduleMemberDTO);
        try {
            ResponseVM<CandidateScheduleMemberDTO> responseVM =
                candidateScheduleMemberComponent.changeStatue(candidateScheduleMemberDTO.getCandidateScheduleId(), candidateScheduleMemberDTO.getStatus());
            return new ResponseEntity<>(responseVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'SCHEDULE_MEMBER','VIEW_SCHEDULE_MEMBER')")
    public ResponseEntity<ResponseVM<CandidateScheduleMemberDTO>> findById(@PathVariable("id") Long id) {
        log.debug("REST Request to find candidateScheduleMember by id : {}", id);
        try {
            ResponseVM<CandidateScheduleMemberDTO> responseVM = candidateScheduleMemberComponent.findById(id);
            return new ResponseEntity<>(responseVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/schedule/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'SCHEDULE','VIEW_SCHEDULE_MEMBER')")
    public ResponseEntity<List<ResponseVM<CandidateScheduleMemberDTO>>> findByScheduleId(@PathVariable("id") Long id) {
        log.debug("REST Request to find candidateScheduleMember by scheduleId : {}", id);
        try {
            List<ResponseVM<CandidateScheduleMemberDTO>> responseVMS =
                candidateScheduleMemberComponent.findByScheduleId(id);
            return new ResponseEntity<>(responseVMS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'SCHEDULE_MEMBER','DELETE_SCHEDULE_MEMBER')")
    public void delete(@PathVariable("id") Long id) {
        log.debug("REST Request to delete candidateScheduleMember by id : {}", id);
        try {
            candidateScheduleMemberService.delete(id);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
