package io.avand.service.impl;

import io.avand.domain.CandidateEntity;
import io.avand.domain.FileEntity;
import io.avand.domain.JobEntity;
import io.avand.repository.CandidateRepository;
import io.avand.repository.FileRepository;
import io.avand.repository.JobRepository;
import io.avand.service.CandidateService;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.mapper.CandidateMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final FileRepository fileRepository;
    private final CandidateMapper candidateMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                JobRepository jobRepository,
                                FileRepository fileRepository,
                                CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.fileRepository = fileRepository;
        this.candidateMapper = candidateMapper;
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
    public List<CandidateDTO> findAll() {
        log.debug("Request to find all candidate");
        return candidateRepository.findAll()
            .stream()
            .map(candidateMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete candidate by id : {}", id);
        candidateRepository.delete(id);
    }
}
