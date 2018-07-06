package io.avand.service.impl;

import io.avand.domain.CompanyEntity;
import io.avand.domain.JobEntity;
import io.avand.repository.CompanyRepository;
import io.avand.repository.JobRepository;
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

    public JobServiceImpl(JobRepository jobRepository,
                          CompanyRepository companyRepository,
                          JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public JobDTO save(JobDTO jobDTO) throws NotFoundException {
        log.debug("Request to save job : {}", jobDTO);
        CompanyEntity companyEntity = companyRepository.findOne(jobDTO.getCompanyId());
        if (companyEntity != null) {
            JobEntity jobEntity = jobMapper.toEntity(jobDTO);
            jobEntity.setCompany(companyEntity);
            jobEntity = jobRepository.save(jobEntity);
            return jobMapper.toDto(jobEntity);
        } else {
            throw new NotFoundException("Company Not Available");
        }
    }

    @Override
    public JobDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find job by id : {}", id);
        JobEntity jobEntity = jobRepository.findOne(id);
        if (jobEntity != null) {
            return jobMapper.toDto(jobEntity);
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

    @Override
    public List<JobDTO> findAll() {
        log.debug("Request to find all job");
        return jobRepository.findAll()
            .stream()
            .map(jobMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete job by id : {}", id);
        jobRepository.delete(id);
    }
}
