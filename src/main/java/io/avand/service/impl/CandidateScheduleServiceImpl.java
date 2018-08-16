package io.avand.service.impl;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.CandidateScheduleRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CandidateScheduleService;
import io.avand.service.dto.CandidateScheduleDTO;
import io.avand.service.mapper.CandidateScheduleMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateScheduleServiceImpl implements CandidateScheduleService {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleServiceImpl.class);
    private final CandidateScheduleRepository candidateScheduleRepository;
    private final CandidateScheduleMapper candidateScheduleMapper;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final CandidateRepository candidateRepository;

    public CandidateScheduleServiceImpl(CandidateScheduleRepository candidateScheduleRepository,
                                        CandidateScheduleMapper candidateScheduleMapper,
                                        UserRepository userRepository,
                                        SecurityUtils securityUtils,
                                        CandidateRepository candidateRepository) {
        this.candidateScheduleRepository = candidateScheduleRepository;
        this.candidateScheduleMapper = candidateScheduleMapper;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public CandidateScheduleDTO save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException {
        log.debug("Request to save schedule for candidate : {}", candidateScheduleDTO);
        UserEntity ownerEntity = userRepository.findOne(candidateScheduleDTO.getOwner());
        if (ownerEntity != null) {
            CandidateEntity candidateEntity = candidateRepository.findOne(candidateScheduleDTO.getCandidateId());
            if (candidateEntity != null) {
                CandidateScheduleEntity candidateScheduleEntity = candidateScheduleMapper.toEntity(candidateScheduleDTO);
                candidateScheduleEntity.setCandidate(candidateEntity);
                candidateScheduleEntity = candidateScheduleRepository.save(candidateScheduleEntity);
                return candidateScheduleMapper.toDto(candidateScheduleEntity);
            } else {
                throw new NotFoundException("Candidate not Found");
            }
        } else {
            throw new NotFoundException("Owner Not Found");
        }
    }

    @Override
    public CandidateScheduleDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find schedule of candidate by id : {}", id);
        CandidateScheduleEntity candidateScheduleEntity = candidateScheduleRepository.findOne(id);
        if (candidateScheduleEntity != null) {
            if (candidateScheduleEntity.getOwner().equals(securityUtils.getCurrentUserId())) {
                return candidateScheduleMapper.toDto(candidateScheduleEntity);
            } else {
                throw new SecurityException("You Don't Have Access To Get This Info");
            }
        } else {
            throw new NotFoundException("Schedule Not Found");
        }
    }

    @Override
    public List<CandidateScheduleDTO> findByOwnerId() throws NotFoundException {
        log.debug("Request to find schedules of candidates by owner");
        return candidateScheduleRepository
            .findAllByOwner(securityUtils.getCurrentUserId())
            .stream()
            .map(candidateScheduleMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<CandidateScheduleDTO> findByOwnerIdAndDateBetween(ZonedDateTime startDate, ZonedDateTime endDate) throws NotFoundException {
        log.debug("Request to find schedule of candidate by ownerId and dates : {}, {}", startDate, endDate);
        return candidateScheduleRepository
            .findAllByOwnerAndScheduleDateAfterAndScheduleDateBefore(securityUtils.getCurrentUserId(), startDate, endDate)
            .stream()
            .map(candidateScheduleMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<CandidateScheduleDTO> findByCandidateId(Long candidateId) {
        log.debug("Request to find schedules of candidate : {}", candidateId);
        return candidateScheduleRepository
            .findAllByCandidate_Id(candidateId)
            .stream()
            .map(candidateScheduleMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete schedule of candidate");
        CandidateScheduleEntity candidateScheduleEntity = candidateScheduleRepository.findOne(id);
        if (candidateScheduleEntity != null) {
            if (candidateScheduleEntity.getOwner().equals(securityUtils.getCurrentUserId())) {
                candidateScheduleRepository.delete(candidateScheduleEntity);
            } else {
                throw new SecurityException("You Don't Have Access to remove this schedule");
            }
        } else {
            throw new NotFoundException("Schedule Not Found");
        }
    }
}
