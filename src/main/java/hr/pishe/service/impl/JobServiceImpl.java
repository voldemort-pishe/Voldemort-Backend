package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.CompanyEntity;
import hr.pishe.domain.entity.jpa.JobEntity;
import hr.pishe.domain.enumeration.JobStatus;
import hr.pishe.repository.jpa.CompanyRepository;
import hr.pishe.repository.jpa.JobRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.JobService;
import hr.pishe.service.dto.JobDTO;
import hr.pishe.service.mapper.JobMapper;
import hr.pishe.web.rest.vm.JobFilterVM;
import hr.pishe.web.specification.JobSpecification;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final JobSpecification jobSpecification;

    public JobServiceImpl(JobRepository jobRepository,
                          CompanyRepository companyRepository,
                          JobMapper jobMapper,
                          SecurityUtils securityUtils,
                          JobSpecification jobSpecification) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.jobMapper = jobMapper;
        this.securityUtils = securityUtils;
        this.jobSpecification = jobSpecification;
    }

    @Override
    public JobDTO save(JobDTO jobDTO) throws NotFoundException {
        log.debug("Request to save job : {}", jobDTO);
        CompanyEntity companyEntity = companyRepository.findOne(securityUtils.getCurrentCompanyId());
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
    public JobDTO update(JobDTO jobDTO) throws NotFoundException {
        log.debug("Request to update job : {}", jobDTO);
        JobEntity jobEntity = jobRepository.findOne(jobDTO.getId());
        if (jobEntity != null) {
            jobEntity.setDepartment(jobDTO.getDepartment());
            jobEntity.setDescriptionEn(jobDTO.getDescriptionEn());
            jobEntity.setDescriptionFa(jobDTO.getDescriptionFa());
            jobEntity.setLanguage(jobDTO.getLanguage());
            jobEntity.setLocation(jobDTO.getLocation());
            jobEntity.setNameEn(jobDTO.getNameEn());
            jobEntity.setNameFa(jobDTO.getNameFa());
            jobEntity.setType(jobDTO.getType());
            jobEntity = jobRepository.save(jobEntity);
            return jobMapper.toDto(jobEntity);
        } else {
            throw new NotFoundException("Job NotFound");
        }
    }

    @Override
    public void publish(Long jobId, JobStatus status) throws NotFoundException {
        log.debug("Request to publish job : {}, {}", jobId, status);
        JobEntity jobEntity = jobRepository.findOne(jobId);
        if (jobEntity != null) {
            if (status == JobStatus.OPEN) {
                if (jobEntity.getStatus() != JobStatus.OPEN) {
                    if (jobEntity.getJobHireTeam() != null && jobEntity.getJobHireTeam().size() != 0) {
                        jobEntity.setStatus(status);
                        jobRepository.save(jobEntity);
                    } else {
                        throw new NotFoundException("لطفا ابتدا تیم استخدام شغل خود را معلوم کنید");
                    }
                } else {
                    throw new NotFoundException("شغل مورد نظر در حال حاضر در حال انتشار است");
                }
            } else {
                jobEntity.setStatus(status);
                jobRepository.save(jobEntity);
            }
        } else {
            throw new NotFoundException("Job Not Found");
        }
    }

    @Override
    public JobDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find job by id : {}", id);
        JobEntity jobEntity = jobRepository.findByIdAndCompany_Id(id, securityUtils.getCurrentCompanyId());
        if (jobEntity != null) {
            return jobMapper.toDto(jobEntity);
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

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
        return jobRepository.findAllByCompany_SubDomainAndStatus(subDomain, JobStatus.OPEN)
            .stream()
            .map(jobMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Page<JobDTO> findAllByFilter(Pageable pageable, JobFilterVM filterVM) throws NotFoundException {
        log.debug("Request to find all job by filter : {}", filterVM);
        if (filterVM == null)
            filterVM = new JobFilterVM();
        if (filterVM.getHireTeam() != null && filterVM.getHireTeam())
            filterVM.setManager(securityUtils.getCurrentUserId());
        filterVM.setCompany(securityUtils.getCurrentCompanyId());
        return jobRepository.findAll(jobSpecification.getFilter(filterVM), pageable)
            .map(jobMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete job by id : {}", id);
        JobEntity jobEntity = jobRepository.findOne(id);
        if (jobEntity != null) {
            jobRepository.delete(jobEntity);
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }
}
