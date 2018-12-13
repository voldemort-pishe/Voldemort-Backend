package io.avand.service.impl;

import io.avand.aop.event.CustomEvent;
import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.domain.entity.jpa.CandidateScheduleMemberEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.enumeration.CandidateScheduleMemberStatus;
import io.avand.domain.enumeration.EventType;
import io.avand.repository.jpa.CandidateScheduleMemberRepository;
import io.avand.repository.jpa.CandidateScheduleRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CandidateScheduleMemberService;
import io.avand.service.dto.CandidateScheduleMemberDTO;
import io.avand.service.mapper.CandidateScheduleMemberMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidateScheduleMemberServiceImpl implements CandidateScheduleMemberService {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleMemberServiceImpl.class);

    private final CandidateScheduleMemberRepository candidateScheduleMemberRepository;
    private final CandidateScheduleMemberMapper candidateScheduleMemberMapper;
    private final CandidateScheduleRepository candidateScheduleRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final ApplicationEventPublisher eventPublisher;

    public CandidateScheduleMemberServiceImpl(CandidateScheduleMemberRepository candidateScheduleMemberRepository,
                                              CandidateScheduleMemberMapper candidateScheduleMemberMapper,
                                              CandidateScheduleRepository candidateScheduleRepository,
                                              UserRepository userRepository,
                                              SecurityUtils securityUtils,
                                              ApplicationEventPublisher eventPublisher) {
        this.candidateScheduleMemberRepository = candidateScheduleMemberRepository;
        this.candidateScheduleMemberMapper = candidateScheduleMemberMapper;
        this.candidateScheduleRepository = candidateScheduleRepository;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CandidateScheduleMemberDTO save(CandidateScheduleMemberDTO candidateScheduleMemberDTO) throws NotFoundException {
        log.debug("Request to save candidateScheduleMember : {}", candidateScheduleMemberDTO);
        CandidateScheduleEntity candidateScheduleEntity =
            candidateScheduleRepository.findOne(candidateScheduleMemberDTO.getCandidateScheduleId());
        if (candidateScheduleEntity != null) {
            UserEntity userEntity = userRepository.findOne(candidateScheduleMemberDTO.getUserId());
            if (userEntity != null) {
                CandidateScheduleMemberEntity candidateScheduleMemberEntity =
                    candidateScheduleMemberMapper.toEntity(candidateScheduleMemberDTO);
                candidateScheduleMemberEntity.setUser(userEntity);
                candidateScheduleMemberEntity.setCandidateSchedule(candidateScheduleEntity);
                candidateScheduleMemberEntity = candidateScheduleMemberRepository.save(candidateScheduleMemberEntity);

                return candidateScheduleMemberMapper.toDto(candidateScheduleMemberEntity);
            } else {
                throw new NotFoundException("User Not Found");
            }
        } else {
            throw new NotFoundException("Candidate Schedule Not Found");
        }
    }

    @Override
    public Set<CandidateScheduleMemberDTO> saveAll(Set<CandidateScheduleMemberDTO> candidateScheduleMemberDTOS) throws NotFoundException {
        log.debug("Request to save candidateScheduleMembers");
        Set<CandidateScheduleMemberDTO> results = new HashSet<>();
        for (CandidateScheduleMemberDTO candidateScheduleMemberDTO : candidateScheduleMemberDTOS) {
            results.add(this.save(candidateScheduleMemberDTO));
        }
        return results;
    }

    @Override
    public CandidateScheduleMemberDTO changeStatus(Long scheduleId, CandidateScheduleMemberStatus status) throws NotFoundException {
        log.debug("Request to change candidateScheduleMembers status : {}, {}", scheduleId, status);
        Long userId = securityUtils.getCurrentUserId();
        CandidateScheduleMemberEntity candidateScheduleMemberEntity =
            candidateScheduleMemberRepository.findByCandidateSchedule_IdAndUser_Id(scheduleId, userId);

        if (candidateScheduleMemberEntity != null) {
            candidateScheduleMemberEntity.setStatus(status);
            candidateScheduleMemberEntity = candidateScheduleMemberRepository.save(candidateScheduleMemberEntity);

            String name = candidateScheduleMemberEntity.getUser().getFirstName() + " " + candidateScheduleMemberEntity.getUser().getLastName();
            List<CandidateScheduleMemberEntity> candidateScheduleMemberEntities =
                candidateScheduleMemberRepository.findAllByCandidateSchedule_Id(scheduleId);

            for (CandidateScheduleMemberEntity scheduleMemberEntity : candidateScheduleMemberEntities) {
                if (!scheduleMemberEntity.getUser().getId().equals(userId)) {
                    CustomEvent customEvent = new CustomEvent(this);
                    customEvent.setTitle(name);
                    customEvent.setDescription(String.format("تغییر وضعیت حضور در مصاحبه به %s", status));
                    customEvent.setType(EventType.ALARM);
                    customEvent.setExtra(scheduleId.toString());
                    customEvent.setOwner(candidateScheduleMemberEntity.getUser().getId());
                    eventPublisher.publishEvent(customEvent);
                }
            }

            return candidateScheduleMemberMapper.toDto(candidateScheduleMemberEntity);
        } else {
            throw new NotFoundException("شما در این مصاحبه حضور ندارید");
        }
    }

    @Override
    public CandidateScheduleMemberDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find candidateSchedule by id : {}", id);
        CandidateScheduleMemberEntity candidateScheduleMemberEntity = candidateScheduleMemberRepository.findOne(id);
        if (candidateScheduleMemberEntity != null) {
            return candidateScheduleMemberMapper.toDto(candidateScheduleMemberEntity);
        } else {
            throw new NotFoundException("Candidate Schedule Member Not Found");
        }
    }

    @Override
    public List<CandidateScheduleMemberDTO> findByScheduleId(Long id) {
        log.debug("Request to find candidateScheduleMember by scheduleId : {}", id);
        return candidateScheduleMemberRepository
            .findAllByCandidateSchedule_Id(id)
            .stream()
            .map(candidateScheduleMemberMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<CandidateScheduleMemberDTO> findByUserId(Long userId) throws NotFoundException {
        log.debug("Request to find candidateScheduleMember by userId : {}", userId);
        return candidateScheduleMemberRepository
            .findAllByUser_Id(securityUtils.getCurrentUserId())
            .stream()
            .map(candidateScheduleMemberMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete candidateScheduleMember : {}", id);
        CandidateScheduleMemberEntity candidateScheduleMemberEntity = candidateScheduleMemberRepository.findOne(id);
        if (candidateScheduleMemberEntity != null) {
            candidateScheduleMemberRepository.delete(candidateScheduleMemberEntity);
        } else {
            throw new NotFoundException("CandidateScheduleMember Not Found");
        }
    }
}
