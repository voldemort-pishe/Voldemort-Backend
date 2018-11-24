package io.avand.service.impl;

import io.avand.aop.event.CustomEvent;
import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.domain.entity.jpa.JobHireTeamEntity;
import io.avand.domain.enumeration.EventType;
import io.avand.mailgun.repository.impl.MailGunMessageRepositoryImpl;
import io.avand.mailgun.service.dto.request.MailGunSendMessageRequestDTO;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.CandidateScheduleRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CalendarService;
import io.avand.service.CandidateScheduleService;
import io.avand.service.dto.CalendarICSAttendeeDTO;
import io.avand.service.dto.CalendarICSCompanyDTO;
import io.avand.service.dto.CalendarICSDTO;
import io.avand.service.dto.CandidateScheduleDTO;
import io.avand.service.mapper.CandidateScheduleMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateScheduleServiceImpl implements CandidateScheduleService {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleServiceImpl.class);
    private final CandidateScheduleRepository candidateScheduleRepository;
    private final CandidateScheduleMapper candidateScheduleMapper;
    private final SecurityUtils securityUtils;
    private final CandidateRepository candidateRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MailGunMessageRepositoryImpl mailGunMessageRepository;
    private final CalendarService calendarService;

    public CandidateScheduleServiceImpl(CandidateScheduleRepository candidateScheduleRepository,
                                        CandidateScheduleMapper candidateScheduleMapper,
                                        SecurityUtils securityUtils,
                                        CandidateRepository candidateRepository,
                                        ApplicationEventPublisher eventPublisher,
                                        MailGunMessageRepositoryImpl mailGunMessageRepository,
                                        CalendarService calendarService) {
        this.candidateScheduleRepository = candidateScheduleRepository;
        this.candidateScheduleMapper = candidateScheduleMapper;
        this.securityUtils = securityUtils;
        this.candidateRepository = candidateRepository;
        this.eventPublisher = eventPublisher;
        this.mailGunMessageRepository = mailGunMessageRepository;
        this.calendarService = calendarService;
    }

    @Override
    public CandidateScheduleDTO save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException, IOException, URISyntaxException {
        log.debug("Request to save schedule for candidate : {}", candidateScheduleDTO);
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
            eventPublisher.publishEvent(customEvent);

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
            for (JobHireTeamEntity jobHireTeamEntity : candidateEntity.getJob().getJobHireTeam()) {
                CalendarICSAttendeeDTO calendarICSAttendeeDTO = new CalendarICSAttendeeDTO();
                calendarICSAttendeeDTO.setName(jobHireTeamEntity.getUser().getFirstName() + " " + jobHireTeamEntity.getUser().getLastName());
                calendarICSAttendeeDTO.setEmail(jobHireTeamEntity.getUser().getEmail());
                calendarICSAttendeeDTOS.add(calendarICSAttendeeDTO);
            }
            calendarICSDTO.setAttendeeDTOList(calendarICSAttendeeDTOS);
            String fileName = calendarService.createICSFile(calendarICSDTO);

            MailGunSendMessageRequestDTO mailGunSendMessageRequestDTO = new MailGunSendMessageRequestDTO();
            mailGunSendMessageRequestDTO.setFromName(candidateEntity.getJob().getCompany().getNameFa());
            mailGunSendMessageRequestDTO.setTo(candidateEntity.getEmail());
            mailGunSendMessageRequestDTO.setSubject("وقت مصاحبه");
            mailGunSendMessageRequestDTO.setText("با سلام برای شما در شرکت وقت مصاحبه تنطیم شده است");
            mailGunSendMessageRequestDTO.setAttachment(new File("/Users/pouya/Project/Avand/voldemort-backend/" + fileName));
            mailGunMessageRepository.send(mailGunSendMessageRequestDTO);

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
