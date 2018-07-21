package io.avand.service.impl;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CandidateMessageEntity;
import io.avand.repository.jpa.CandidateMessageRepository;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.service.CandidateMessageService;
import io.avand.service.dto.CandidateMessageDTO;
import io.avand.service.mapper.CandidateMessageMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateMessageServiceImpl implements CandidateMessageService {

    private final Logger log = LoggerFactory.getLogger(CandidateMessageServiceImpl.class);
    private final CandidateMessageRepository candidateMessageRepository;
    private final CandidateMessageMapper candidateMessageMapper;
    private final CandidateRepository candidateRepository;

    public CandidateMessageServiceImpl(CandidateMessageRepository candidateMessageRepository,
                                       CandidateMessageMapper candidateMessageMapper,
                                       CandidateRepository candidateRepository) {
        this.candidateMessageRepository = candidateMessageRepository;
        this.candidateMessageMapper = candidateMessageMapper;
        this.candidateRepository = candidateRepository;
    }


    @Override
    public CandidateMessageDTO save(CandidateMessageDTO candidateMessageDTO) throws NotFoundException {
        log.debug("Request to save candidateMessage : {}", candidateMessageDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(candidateMessageDTO.getCandidateId());
        if (candidateEntity != null) {
            CandidateMessageEntity parentMessage = null;

            if (candidateMessageDTO.getParentId() != null) {
                parentMessage = candidateMessageRepository.findOne(candidateMessageDTO.getParentId());
                if (parentMessage == null) {
                    throw new NotFoundException("Parent Message Not Found");
                }
            }

            CandidateMessageEntity candidateMessageEntity = candidateMessageMapper.toEntity(candidateMessageDTO);
            candidateMessageEntity.setCandidate(candidateEntity);
            candidateMessageEntity.setParent(parentMessage);
            candidateMessageEntity = candidateMessageRepository.save(candidateMessageEntity);
            return candidateMessageMapper.toDto(candidateMessageEntity);
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public CandidateMessageDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find candidate Message by id : {}", id);
        CandidateMessageEntity candidateMessageEntity = candidateMessageRepository.findOne(id);
        if (candidateMessageEntity != null) {
            return candidateMessageMapper.toDto(candidateMessageEntity);
        } else {
            throw new NotFoundException("Candidate Message Not Available");
        }
    }

    @Override
    public List<CandidateMessageDTO> findByCandidateId(Long candidateId) {
        log.debug("Request to find all candidate message by candidate id : {}", candidateId);
        return candidateMessageRepository.findAllByCandidate_Id(candidateId)
            .stream()
            .map(candidateMessageMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete candidate message by id : {}", id);
        CandidateMessageEntity candidateMessageEntity = candidateMessageRepository.findOne(id);
        if (candidateMessageEntity != null) {
            candidateMessageRepository.delete(candidateMessageEntity);
        } else {
            throw new NotFoundException("Candidate Message Not Available");
        }
    }
}
