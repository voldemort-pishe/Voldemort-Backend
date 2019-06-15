package hr.pishe.service;

import hr.pishe.service.dto.JobHireTeamDTO;
import javassist.NotFoundException;

import java.util.List;

public interface JobHireTeamService {

    JobHireTeamDTO save(JobHireTeamDTO jobHireTeamDTO) throws NotFoundException;

    List<JobHireTeamDTO> saveAll(List<JobHireTeamDTO> jobHireTeamDTOs) throws NotFoundException;

    List<JobHireTeamDTO> findByJobId(Long jobId) throws NotFoundException;

    List<JobHireTeamDTO> findAllByUserLoginAndJobId(String login, Long jobId);

    void delete(Long id) throws NotFoundException;
}
