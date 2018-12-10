package io.avand.service.impl;

import io.avand.aop.event.CustomEvent;
import io.avand.config.ApplicationProperties;
import io.avand.config.StorageProperties;
import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.enumeration.AttendeeRoleType;
import io.avand.domain.enumeration.EventType;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.CandidateScheduleRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CalendarService;
import io.avand.service.CandidateScheduleMemberService;
import io.avand.service.CandidateScheduleService;
import io.avand.service.MailService;
import io.avand.service.dto.*;
import io.avand.service.mapper.CandidateScheduleMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CandidateScheduleServiceImpl implements CandidateScheduleService {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleServiceImpl.class);
    private final CandidateScheduleRepository candidateScheduleRepository;
    private final CandidateScheduleMapper candidateScheduleMapper;
    private final CandidateScheduleMemberService candidateScheduleMemberService;
    private final SecurityUtils securityUtils;
    private final CandidateRepository candidateRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MailService mailService;
    private final CalendarService calendarService;
    private final UserRepository userRepository;
    private final StorageProperties storageProperties;

    public CandidateScheduleServiceImpl(CandidateScheduleRepository candidateScheduleRepository,
                                        CandidateScheduleMapper candidateScheduleMapper,
                                        CandidateScheduleMemberService candidateScheduleMemberService,
                                        SecurityUtils securityUtils,
                                        CandidateRepository candidateRepository,
                                        ApplicationEventPublisher eventPublisher,
                                        MailService mailService,
                                        CalendarService calendarService,
                                        UserRepository userRepository,
                                        StorageProperties storageProperties) {
        this.candidateScheduleRepository = candidateScheduleRepository;
        this.candidateScheduleMapper = candidateScheduleMapper;
        this.candidateScheduleMemberService = candidateScheduleMemberService;
        this.securityUtils = securityUtils;
        this.candidateRepository = candidateRepository;
        this.eventPublisher = eventPublisher;
        this.mailService = mailService;
        this.calendarService = calendarService;
        this.userRepository = userRepository;
        this.storageProperties = storageProperties;
    }

    @Override
    @Transactional
    public CandidateScheduleDTO save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException, IOException, URISyntaxException {
        log.debug("Request to save schedule for candidate : {}", candidateScheduleDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(candidateScheduleDTO.getCandidateId());
        if (candidateEntity != null) {

            Set<CandidateScheduleMemberDTO> candidateScheduleMemberDTOS = candidateScheduleDTO.getMember();
            candidateScheduleDTO.setMember(null);

            CandidateScheduleEntity candidateScheduleEntity = candidateScheduleMapper.toEntity(candidateScheduleDTO);
            candidateScheduleEntity.setCandidate(candidateEntity);
            candidateScheduleEntity = candidateScheduleRepository.save(candidateScheduleEntity);

            for (CandidateScheduleMemberDTO candidateScheduleMemberDTO : candidateScheduleMemberDTOS) {
                candidateScheduleMemberDTO.setCandidateScheduleId(candidateScheduleEntity.getId());
            }
            candidateScheduleMemberService.saveAll(candidateScheduleMemberDTOS);

            String name = candidateEntity.getFirstName() + " " + candidateEntity.getLastName();
            for (CandidateScheduleMemberDTO candidateScheduleMemberDTO : candidateScheduleMemberDTOS) {
                CustomEvent customEvent = new CustomEvent(this);
                customEvent.setTitle(name);
                customEvent.setDescription("۱ رویداد زمان‌بندی شده");
                customEvent.setType(EventType.SCHEDULE);
                customEvent.setExtra(candidateScheduleEntity.getId().toString());
                customEvent.setOwner(candidateScheduleMemberDTO.getUserId());
                eventPublisher.publishEvent(customEvent);
            }

            CalendarICSDTO calendarICSDTO = new CalendarICSDTO();
            calendarICSDTO.setStartDate(candidateScheduleEntity.getStartDate());
            calendarICSDTO.setEndDate(candidateScheduleEntity.getEndDate());
            calendarICSDTO.setSummery("وقت مصاحبه");

            CalendarICSCompanyDTO calendarICSCompanyDTO = new CalendarICSCompanyDTO();
            calendarICSCompanyDTO.setName(candidateEntity.getJob().getCompany().getNameFa());
            calendarICSCompanyDTO.setAddress(candidateEntity.getJob().getCompany().getContact().getAddress());
            calendarICSCompanyDTO.setEmail(candidateEntity.getJob().getCompany().getContact().getEmail());
            calendarICSDTO.setCompany(calendarICSCompanyDTO);

            List<CalendarICSAttendeeDTO> calendarICSAttendeeDTOS = new ArrayList<>();
            for (CandidateScheduleMemberDTO candidateScheduleMemberDTO : candidateScheduleMemberDTOS) {
                Optional<UserEntity> userEntity = userRepository.findById(candidateScheduleMemberDTO.getUserId());
                if (userEntity.isPresent()) {
                    CalendarICSAttendeeDTO calendarICSAttendeeDTO = new CalendarICSAttendeeDTO();
                    calendarICSAttendeeDTO.setName(userEntity.get().getFirstName() + " " + userEntity.get().getLastName());
                    calendarICSAttendeeDTO.setEmail(userEntity.get().getEmail());
                    calendarICSAttendeeDTO.setRole(AttendeeRoleType.HIRE_TEAM);
                    calendarICSAttendeeDTOS.add(calendarICSAttendeeDTO);
                }
            }

            CalendarICSAttendeeDTO calendarICSAttendeeDTO = new CalendarICSAttendeeDTO();
            calendarICSAttendeeDTO.setName(candidateEntity.getFirstName() + " " + candidateEntity.getLastName());
            calendarICSAttendeeDTO.setEmail(candidateEntity.getEmail());
            calendarICSAttendeeDTO.setRole(AttendeeRoleType.CANDIDATE);
            calendarICSAttendeeDTOS.add(calendarICSAttendeeDTO);

            calendarICSDTO.setAttendeeDTOList(calendarICSAttendeeDTOS);
            String fileName = calendarService.createICSFile(calendarICSDTO);

            File attachment = new File(storageProperties.getLocation() + "/" + fileName);

            for (CalendarICSAttendeeDTO icsAttendeeDTO : calendarICSAttendeeDTOS) {
                if (icsAttendeeDTO.getRole() == AttendeeRoleType.CANDIDATE) {
                    mailService.sendScheduleCandidate(candidateScheduleEntity, icsAttendeeDTO.getName(), icsAttendeeDTO.getEmail(), attachment);
                } else {
                    mailService.sendScheduleTeam(candidateScheduleEntity, icsAttendeeDTO.getName(), icsAttendeeDTO.getEmail(), attachment);
                }
            }

            return candidateScheduleMapper.toDto(candidateScheduleEntity);
        } else {
            throw new NotFoundException("Candidate not Found");
        }
    }

    @Override
    public CandidateScheduleDTO update(CandidateScheduleDTO candidateScheduleDTO) {
        log.debug("Request to update schedule for candidate : {}", candidateScheduleDTO);
        CandidateScheduleEntity previousSchedule = candidateScheduleRepository.findOne(candidateScheduleDTO.getId());
        previousSchedule.setStartDate(candidateScheduleDTO.getStartDate());
        previousSchedule.setEndDate(candidateScheduleDTO.getEndDate());
        previousSchedule.setLocation(candidateScheduleDTO.getLocation());
        previousSchedule.setDescription(candidateScheduleDTO.getDescription());
        previousSchedule.setStatus(candidateScheduleDTO.getStatus());

        String name = previousSchedule.getCandidate().getFirstName() + " " + previousSchedule.getCandidate().getLastName();

        CustomEvent customEvent = new CustomEvent(this);
        customEvent.setTitle(name);
        customEvent.setDescription("تغییر زمان رویداد");
        customEvent.setType(EventType.SCHEDULE);
        customEvent.setExtra(previousSchedule.getId().toString());
        eventPublisher.publishEvent(customEvent);

        previousSchedule = candidateScheduleRepository.save(previousSchedule);
        return candidateScheduleMapper.toDto(previousSchedule);
    }

    @Override
    public CandidateScheduleDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find schedule of candidate by id : {}", id);
        CandidateScheduleEntity candidateScheduleEntity = candidateScheduleRepository.findOne(id);
        if (candidateScheduleEntity != null) {
            return candidateScheduleMapper.toDto(candidateScheduleEntity);
        } else {
            throw new NotFoundException("Schedule Not Found");
        }
    }

    @Override
    public Page<CandidateScheduleDTO> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to find schedules of candidates by owner");
        return candidateScheduleRepository
            .findAllByCandidate_Job_Company_Id(securityUtils.getCurrentCompanyId(), pageable)
            .map(candidateScheduleMapper::toDto);
    }

    @Override
    public Page<CandidateScheduleDTO> findByDate(ZonedDateTime startDate, ZonedDateTime endDate, Pageable pageable)
        throws NotFoundException {
        log.debug("Request to find schedule of candidate by ownerId and dates : {}, {}", startDate, endDate);
        return candidateScheduleRepository
            .findAllByCandidate_Job_Company_IdAndStartDateBeforeAndEndDateAfter
                (securityUtils.getCurrentCompanyId(), startDate, endDate, pageable)
            .map(candidateScheduleMapper::toDto);
    }

    @Override
    public Page<CandidateScheduleDTO> findByCandidateId(Long candidateId, Pageable pageable)
        throws NotFoundException {
        log.debug("Request to find schedules of candidate : {}", candidateId);
        return candidateScheduleRepository
            .findAllByCandidate_IdAndCandidate_Job_Company_Id
                (candidateId, securityUtils.getCurrentCompanyId(), pageable)
            .map(candidateScheduleMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete schedule of candidate");
        CandidateScheduleEntity candidateScheduleEntity = candidateScheduleRepository.findOne(id);
        if (candidateScheduleEntity != null) {
            candidateScheduleRepository.delete(candidateScheduleEntity);
        } else {
            throw new NotFoundException("Schedule Not Found");
        }
    }
}
