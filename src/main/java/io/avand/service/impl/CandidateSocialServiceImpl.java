package io.avand.service.impl;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CandidateSocialEntity;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.CandidateSocialRepository;
import io.avand.service.CandidateSocialService;
import io.avand.service.dto.CandidateSocialDTO;
import io.avand.service.mapper.CandidateSocialMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateSocialServiceImpl implements CandidateSocialService {

    private final Logger log = LoggerFactory.getLogger(CandidateSocialServiceImpl.class);
    private final CandidateSocialRepository candidateSocialRepository;
    private final CandidateRepository candidateRepository;
    private final CandidateSocialMapper candidateSocialMapper;

    public CandidateSocialServiceImpl(CandidateSocialRepository candidateSocialRepository,
                                      CandidateRepository candidateRepository,
                                      CandidateSocialMapper candidateSocialMapper) {
        this.candidateSocialRepository = candidateSocialRepository;
        this.candidateRepository = candidateRepository;
        this.candidateSocialMapper = candidateSocialMapper;
    }

    @Override
    public CandidateSocialDTO save(CandidateSocialDTO candidateSocialDTO) throws NotFoundException {
        log.debug("Request to save candidateSocial : {}", candidateSocialDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(candidateSocialDTO.getCandidateId());
        if (candidateEntity != null) {
            CandidateSocialEntity candidateSocialEntity = candidateSocialMapper.toEntity(candidateSocialDTO);
            candidateSocialEntity.setCandidate(candidateEntity);
            candidateSocialEntity = candidateSocialRepository.save(candidateSocialEntity);
            return candidateSocialMapper.toDto(candidateSocialEntity);
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public CandidateSocialDTO findById(Long id) {
        log.debug("Request to find candidateSocial by Id : {}", id);
        CandidateSocialEntity candidateSocialEntity = candidateSocialRepository.findOne(id);
        return candidateSocialMapper.toDto(candidateSocialEntity);
    }

    @Override
    public List<CandidateSocialDTO> findAllByCandidateId(Long candidateId) {
        log.debug("Request to find candidateSocial by candidateId : {}", candidateId);
        return candidateSocialRepository
            .findAllByCandidate_Id(candidateId)
            .stream()
            .map(candidateSocialMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete candidateSocial : {}", id);
        CandidateSocialEntity candidateSocialEntity = candidateSocialRepository.findOne(id);
        if (candidateSocialEntity != null) {
            candidateSocialRepository.delete(candidateSocialEntity);
        } else {
            throw new NotFoundException("CandidateSocial Not Found");
        }
    }
}
