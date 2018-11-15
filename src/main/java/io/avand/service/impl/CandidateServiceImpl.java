package io.avand.service.impl;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.FileEntity;
import io.avand.domain.entity.jpa.JobEntity;
import io.avand.domain.enumeration.CandidateState;
import io.avand.domain.enumeration.CandidateType;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.FileRepository;
import io.avand.repository.jpa.JobRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CandidateService;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.mapper.CandidateMapper;
import io.avand.web.rest.vm.CandidateFilterVM;
import io.avand.web.specification.CandidateSpecification;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final FileRepository fileRepository;
    private final CandidateMapper candidateMapper;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final CandidateSpecification specification;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                JobRepository jobRepository,
                                FileRepository fileRepository,
                                CandidateMapper candidateMapper,
                                SecurityUtils securityUtils,
                                UserRepository userRepository,
                                CandidateSpecification specification) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.fileRepository = fileRepository;
        this.candidateMapper = candidateMapper;
        this.securityUtils = securityUtils;

        this.userRepository = userRepository;
        this.specification = specification;
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO) throws NotFoundException {
        log.debug("Request to save candidate : {}", candidateDTO);
        JobEntity jobEntity = jobRepository.findOne(candidateDTO.getJobId());
        if (jobEntity != null) {
            FileEntity fileEntity = fileRepository.findOne(candidateDTO.getFileId());
            if (fileEntity != null) {
                CandidateEntity candidateEntity = candidateMapper.toEntity(candidateDTO);
                candidateEntity.setJob(jobEntity);
                candidateEntity.setFile(fileEntity);
                candidateEntity.setEmployer(candidateDTO.getEmployer());
                candidateEntity = candidateRepository.save(candidateEntity);
                return candidateMapper.toDto(candidateEntity);
            } else {
                throw new NotFoundException("File Not Available");
            }
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO, String companySubDomain) throws NotFoundException {
        log.debug("Request to save candidate by subDomain : {}, {}", candidateDTO, companySubDomain);
        JobEntity jobEntity = jobRepository.findOne(candidateDTO.getJobId());
        if (jobEntity != null) {
            if (jobEntity.getCompany().getSubDomain().equals(companySubDomain)) {
                FileEntity fileEntity = fileRepository.findOne(candidateDTO.getFileId());
                if (fileEntity != null) {
                    CandidateEntity candidateEntity = candidateMapper.toEntity(candidateDTO);
                    candidateEntity.setType(CandidateType.APPLICANT);
                    candidateEntity.setState(CandidateState.PENDING);
                    candidateEntity.setJob(jobEntity);
                    candidateEntity.setFile(fileEntity);
                    candidateEntity = candidateRepository.save(candidateEntity);
                    return candidateMapper.toDto(candidateEntity);
                } else {
                    throw new NotFoundException("File Not Available");
                }
            } else {
                throw new SecurityException("You Don't Have Access To Create Candidate For This Company");
            }
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

    @Override
    public CandidateDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find candidate by id : {}", id);
        CandidateEntity candidateEntity = candidateRepository.findOne(id);
        if (candidateEntity != null) {
            return candidateMapper.toDto(candidateEntity);
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public Page<CandidateDTO> findAll(Pageable pageable) {
        log.debug("Request to find all candidate");
        return candidateRepository.findAll(pageable)
            .map(candidateMapper::toDto);
    }

    @Override
    public Page<CandidateDTO> findByJobId(CandidateFilterVM filterVM, Pageable pageable) throws NotFoundException {
        return candidateRepository
            .findAll(specification.getFilter(filterVM), pageable)
            .map(candidateMapper::toDto);
    }

    @Override
    public Page<CandidateDTO> findByCompanyId(CandidateFilterVM filterVM, Pageable pageable) throws NotFoundException {
        return candidateRepository
            .findAll(specification.getFilter(filterVM), pageable)
            .map(candidateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete candidate by id : {}", id);
        candidateRepository.delete(id);
    }
}
