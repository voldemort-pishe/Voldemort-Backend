package io.avand.service.impl;

import io.avand.aop.event.CustomEvent;
import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.enumeration.EventType;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class CandidateScheduleServiceImpl implements CandidateScheduleService {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleServiceImpl.class);
    private final CandidateScheduleRepository candidateScheduleRepository;
    private final CandidateScheduleMapper candidateScheduleMapper;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final CandidateRepository candidateRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CandidateScheduleServiceImpl(CandidateScheduleRepository candidateScheduleRepository,
                                        CandidateScheduleMapper candidateScheduleMapper,
                                        UserRepository userRepository,
                                        SecurityUtils securityUtils,
                                        CandidateRepository candidateRepository,
                                        ApplicationEventPublisher eventPublisher) {
        this.candidateScheduleRepository = candidateScheduleRepository;
        this.candidateScheduleMapper = candidateScheduleMapper;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.candidateRepository = candidateRepository;
        this.eventPublisher = eventPublisher;
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

                String name = candidateEntity.getFirstName() + " " + candidateEntity.getLastName();

                CustomEvent customEvent = new CustomEvent(this);
                customEvent.setTitle(name);
                customEvent.setDescription("۱ رویداد زمان‌بندی شده");
                customEvent.setType(EventType.SCHEDULE);
                customEvent.setExtra(candidateScheduleEntity.getId().toString());
                customEvent.setOwner(ownerEntity.getId());
                eventPublisher.publishEvent(customEvent);

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
    public Page<CandidateScheduleDTO> findByOwnerId(Pageable pageable) throws NotFoundException {
        log.debug("Request to find schedules of candidates by owner");
        return candidateScheduleRepository
            .findAllByOwner(securityUtils.getCurrentUserId(), pageable)
            .map(candidateScheduleMapper::toDto);
    }

    @Override
    public Page<CandidateScheduleDTO> findByOwnerIdAndDateBetween(ZonedDateTime startDate, ZonedDateTime endDate, Pageable pageable) throws NotFoundException {
        log.debug("Request to find schedule of candidate by ownerId and dates : {}, {}", startDate, endDate);
        return candidateScheduleRepository
            .findAllByOwnerAndScheduleDateAfterAndScheduleDateBefore(securityUtils.getCurrentUserId(), startDate, endDate, pageable)
            .map(candidateScheduleMapper::toDto);
    }

    @Override
    public Page<CandidateScheduleDTO> findByCandidateId(Long candidateId, Pageable pageable) {
        log.debug("Request to find schedules of candidate : {}", candidateId);
        return candidateScheduleRepository
            .findAllByCandidate_Id(candidateId, pageable)
            .map(candidateScheduleMapper::toDto);
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
