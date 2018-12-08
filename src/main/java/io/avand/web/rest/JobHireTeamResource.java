package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.JobHireTeamService;
import io.avand.service.dto.JobHireTeamDTO;
import io.avand.web.rest.component.JobHireTeamComponent;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.JobHireTeamVM;
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
@RequestMapping("/api/job-hire-team")
public class JobHireTeamResource {

    private final Logger log = LoggerFactory.getLogger(JobHireTeamResource.class);
    private final JobHireTeamComponent jobHireTeamComponent;
    private final JobHireTeamService jobHireTeamService;

    public JobHireTeamResource(JobHireTeamComponent jobHireTeamComponent,
                               JobHireTeamService jobHireTeamService) {
        this.jobHireTeamComponent = jobHireTeamComponent;
        this.jobHireTeamService = jobHireTeamService;
    }

    @PostMapping
    @Timed
    @PreAuthorize("isMember(#jobHireTeamVM.jobId,'JOB','ADD_JOB_MEMBER')")
    public ResponseEntity<List<ResponseVM<JobHireTeamDTO>>> save(@Valid @RequestBody JobHireTeamVM jobHireTeamVM) {
        log.debug("REST Request to save jobHireTeams");
        try {
            List<ResponseVM<JobHireTeamDTO>> responseVMS = jobHireTeamComponent.saveAll(jobHireTeamVM.getTeams());
            return new ResponseEntity<>(responseVMS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/job/{id}")
    @Timed
    @PreAuthorize("isMember(#jobId,'JOB','VIEW_JOB_MEMBER')")
    public ResponseEntity<List<ResponseVM<JobHireTeamDTO>>> getByJobId(@PathVariable("id") Long jobId) {
        log.debug("REST Request to find jobHireTeam by jobId : {}", jobId);
        try {
            List<ResponseVM<JobHireTeamDTO>> responseVMS = jobHireTeamComponent.findByJobId(jobId);
            return new ResponseEntity<>(responseVMS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'JOB_HIRE_TEAM','DELETE_JOB_MEMBER')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        log.debug("REST Request to delete jobHireTeam : {}", id);
        try {
            jobHireTeamService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
