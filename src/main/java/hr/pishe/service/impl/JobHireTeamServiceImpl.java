package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.JobEntity;
import hr.pishe.domain.entity.jpa.JobHireTeamEntity;
import hr.pishe.domain.entity.jpa.UserEntity;
import hr.pishe.repository.jpa.JobHireTeamRepository;
import hr.pishe.repository.jpa.JobRepository;
import hr.pishe.repository.jpa.UserRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.JobHireTeamService;
import hr.pishe.service.dto.JobHireTeamDTO;
import hr.pishe.service.mapper.JobHireTeamMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobHireTeamServiceImpl implements JobHireTeamService {

    private final Logger log = LoggerFactory.getLogger(JobHireTeamServiceImpl.class);

    private final JobHireTeamRepository jobHireTeamRepository;
    private final JobHireTeamMapper jobHireTeamMapper;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final SecurityUtils securityUtils;


    public JobHireTeamServiceImpl(JobHireTeamRepository jobHireTeamRepository,
                                  JobHireTeamMapper jobHireTeamMapper,
                                  UserRepository userRepository,
                                  JobRepository jobRepository,
                                  SecurityUtils securityUtils) {
        this.jobHireTeamRepository = jobHireTeamRepository;
        this.jobHireTeamMapper = jobHireTeamMapper;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public JobHireTeamDTO save(JobHireTeamDTO jobHireTeamDTO) throws NotFoundException {
        log.debug("Request to save jobHireTeam : {}", jobHireTeamDTO);
        JobEntity jobEntity =
            jobRepository.findByIdAndCompany_Id(jobHireTeamDTO.getJobId(), securityUtils.getCurrentCompanyId());
        if (jobEntity != null) {
            UserEntity userEntity = userRepository.findOne(jobHireTeamDTO.getUserId());
            if (userEntity != null) {
                JobHireTeamEntity jobHireTeamEntity = jobHireTeamMapper.toEntity(jobHireTeamDTO);
                jobHireTeamEntity.setJob(jobEntity);
                jobHireTeamEntity.setUser(userEntity);
                jobHireTeamEntity = jobHireTeamRepository.save(jobHireTeamEntity);

                return jobHireTeamMapper.toDto(jobHireTeamEntity);
            } else {
                throw new NotFoundException("User Not Found");
            }
        } else {
            throw new NotFoundException("Job Not Found");
        }
    }

    @Override
    public List<JobHireTeamDTO> saveAll(List<JobHireTeamDTO> jobHireTeamDTOs) throws NotFoundException {
        log.debug("Request to save jobHireTeamDTOs");
        List<JobHireTeamDTO> result = new ArrayList<>();
        for (JobHireTeamDTO jobHireTeamDTO : jobHireTeamDTOs) {
            result.add(this.save(jobHireTeamDTO));
        }
        return result;
    }

    @Override
    public List<JobHireTeamDTO> findByJobId(Long jobId) throws NotFoundException {
        log.debug("Request to jobHireTeam by jobId : {}", jobId);
        return jobHireTeamRepository
            .findByJob_IdAndJob_Company_Id(jobId, securityUtils.getCurrentCompanyId())
            .stream()
            .map(jobHireTeamMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<JobHireTeamDTO> findAllByUserLoginAndJobId(String login, Long jobId) {
        log.debug("Request to ");
        return jobHireTeamRepository
            .findAllByUser_LoginAndJob_Id(login, jobId)
            .stream()
            .map(jobHireTeamMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete jobHireTeamDTO : {}", id);
        JobHireTeamEntity jobHireTeamEntity = jobHireTeamRepository.findOne(id);
        if (jobHireTeamEntity != null) {
            jobHireTeamRepository.delete(jobHireTeamEntity);
        } else {
            throw new NotFoundException("Job Hire Not Found");
        }
    }
}
