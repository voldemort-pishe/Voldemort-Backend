package io.avand.service.impl;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.JobEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.repository.jpa.CompanyRepository;
import io.avand.repository.jpa.JobRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.JobService;
import io.avand.service.dto.JobDTO;
import io.avand.service.mapper.JobMapper;
import io.avand.web.rest.vm.JobFilterVM;
import io.avand.web.specification.JobSpecification;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobMapper jobMapper;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final JobSpecification jobSpecification;

    public JobServiceImpl(JobRepository jobRepository,
                          CompanyRepository companyRepository,
                          JobMapper jobMapper,
                          SecurityUtils securityUtils,
                          UserRepository userRepository,
                          JobSpecification jobSpecification) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.jobMapper = jobMapper;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.jobSpecification = jobSpecification;
    }

    @Override
    public JobDTO save(JobDTO jobDTO) throws NotFoundException {
        log.debug("Request to save job : {}", jobDTO);
        CompanyEntity companyEntity = companyRepository.findOne(jobDTO.getCompanyId());
        if (companyEntity != null) {
            if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
                Optional<UserEntity> hiredManager = userRepository.findById(jobDTO.getHiredManagerId());
                if (hiredManager.isPresent()) {
                    Optional<UserEntity> hiredExpert = userRepository.findById(jobDTO.getHiredExpertId());
                    if (hiredExpert.isPresent()) {
                        JobEntity jobEntity = jobMapper.toEntity(jobDTO);
                        jobEntity.setCompany(companyEntity);
                        jobEntity.setHiredManager(hiredManager.get());
                        jobEntity.setHiredExpert(hiredExpert.get());
                        jobEntity = jobRepository.save(jobEntity);
                        return jobMapper.toDto(jobEntity);
                    } else {
                        throw new NotFoundException("Expert Manager Not Available");
                    }
                } else {
                    throw new NotFoundException("Hired Manager Not Available");
                }
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

    //TODO fix this
    @Override
    public JobDTO findByJobUniqueIdAndCompanySubDomain(String uniqueId, String subDomain) throws NotFoundException {
        log.debug("Request to find job by id and company subDomain");
        JobEntity jobEntity = jobRepository.findByUniqueIdAndCompany_SubDomain(uniqueId, subDomain);
        if (jobEntity != null) {
            return jobMapper.toDto(jobEntity);
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

    @Override
    public List<JobDTO> findAllByCompanySubDomain(String subDomain) {
        log.debug("Request to find job by company subDomain : {}", subDomain);
        return jobRepository.findAllByCompany_SubDomain(subDomain)
            .stream()
            .map(jobMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Page<JobDTO> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to find all job");
        return jobRepository.findAllByCompany_User_Id(securityUtils.getCurrentUserId(), pageable)
            .map(jobMapper::toDto);
    }

    @Override
    public Page<JobDTO> findAllByCompanyId(Pageable pageable, JobFilterVM filterVM) {
        log.debug("Request to find all job");
        return jobRepository.findAll(jobSpecification.getFilter(filterVM), pageable)
            .map(jobMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete job by id : {}", id);
        JobEntity jobEntity = jobRepository.findOne(id);
        if (jobEntity != null) {
            if (jobEntity.getCompany().getUser().getId().equals(securityUtils.getCurrentUserId())) {
                jobRepository.delete(jobEntity);
            } else {
                throw new SecurityException("You Don't Have Access To Delete This Job");
            }
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }
}
