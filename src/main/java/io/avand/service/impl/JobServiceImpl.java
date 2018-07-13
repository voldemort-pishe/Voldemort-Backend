package io.avand.service.impl;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.JobEntity;
import io.avand.repository.jpa.CompanyRepository;
import io.avand.repository.jpa.JobRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.JobService;
import io.avand.service.dto.JobDTO;
import io.avand.service.mapper.JobMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobMapper jobMapper;
    private final SecurityUtils securityUtils;

    public JobServiceImpl(JobRepository jobRepository,
                          CompanyRepository companyRepository,
                          JobMapper jobMapper,
                          SecurityUtils securityUtils) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.jobMapper = jobMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public JobDTO save(JobDTO jobDTO) throws NotFoundException {
        log.debug("Request to save job : {}", jobDTO);
        CompanyEntity companyEntity = companyRepository.findOne(jobDTO.getCompanyId());
        if (companyEntity != null) {
            if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
                JobEntity jobEntity = jobMapper.toEntity(jobDTO);
                jobEntity.setCompany(companyEntity);
                jobEntity = jobRepository.save(jobEntity);
                return jobMapper.toDto(jobEntity);
            } else {
                throw new SecurityException("You Don't Have Access To Create Job For This Company");
            }
        } else {
            throw new NotFoundException("Company Not Available");
        }
    }

    @Override
    public JobDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find job by id : {}", id);
        JobEntity jobEntity = jobRepository.findOne(id);
        if (jobEntity != null) {
            if (jobEntity.getCompany().getUser().getId().equals(securityUtils.getCurrentUserId())) {
                return jobMapper.toDto(jobEntity);
            } else {
                throw new SecurityException("You Don't Have Access To Find This Job");
            }
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

    @Override
    public List<JobDTO> findAll() throws NotFoundException {
        log.debug("Request to find all job");
        return jobRepository.findAllByCompany_User_Id(securityUtils.getCurrentUserId())
            .stream()
            .map(jobMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete job by id : {}", id);
        JobEntity jobEntity = jobRepository.findOne(id);
        if (jobEntity != null) {
            if (jobEntity.getCompany().getUser().getId().equals(securityUtils.getCurrentUserId())) {
                jobRepository.delete(jobEntity);
            }else {
                throw new SecurityException("You Don't Have Access To Delete This Job");
            }
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }
}
