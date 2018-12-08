package io.avand.web.rest.component;

import io.avand.service.dto.JobHireTeamDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

import java.util.List;

public interface JobHireTeamComponent {

    ResponseVM<JobHireTeamDTO> save(JobHireTeamDTO jobHireTeamDTO) throws NotFoundException;

    List<ResponseVM<JobHireTeamDTO>> saveAll(List<JobHireTeamDTO> jobHireTeamDTOS) throws NotFoundException;

    List<ResponseVM<JobHireTeamDTO>> findByJobId(Long jobId) throws NotFoundException;

}
