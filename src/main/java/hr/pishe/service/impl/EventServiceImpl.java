package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.EventEntity;
import hr.pishe.domain.entity.jpa.UserEntity;
import hr.pishe.domain.enumeration.EventStatus;
import hr.pishe.repository.jpa.EventRepository;
import hr.pishe.repository.jpa.UserRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.EventService;
import hr.pishe.service.dto.EventDTO;
import hr.pishe.service.dto.EventTypeCountDTO;
import hr.pishe.service.mapper.EventMapper;
import hr.pishe.web.rest.vm.EventFilterVM;
import hr.pishe.web.specification.EventSpecification;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final EventSpecification specification;

    public EventServiceImpl(EventRepository eventRepository,
                            EventMapper eventMapper,
                            SecurityUtils securityUtils,
                            UserRepository userRepository,
                            EventSpecification specification) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.specification = specification;
    }

    @Override
    public EventDTO save(EventDTO eventDTO) throws NotFoundException {
        log.debug("Request to save event : {}", eventDTO);
        EventEntity eventEntity;
        if (eventDTO.getId() != null) {
            eventEntity = eventRepository.findOne(eventDTO.getId());
            eventEntity.setStatus(eventDTO.getStatus());
            eventEntity.setDescription(eventDTO.getDescription());
            eventEntity.setTitle(eventDTO.getTitle());
        } else {
            eventEntity = eventMapper.toEntity(eventDTO);
            eventEntity.setFlag(false);
            eventEntity.setStatus(EventStatus.UNREAD);
        }
        Optional<UserEntity> userEntity = userRepository.findById(eventDTO.getOwnerId());
        if (userEntity.isPresent()) {
            eventEntity.setOwner(userEntity.get());
            eventEntity = eventRepository.save(eventEntity);
            return eventMapper.toDto(eventEntity);
        } else {
            throw new NotFoundException("User Not Found");
        }
    }

    @Override
    public EventDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find event by id : {}", id);
        try {
            EventEntity eventEntity = eventRepository.findByIdAndOwner_Id(id, securityUtils.getCurrentUserId());
            if (eventEntity != null)
                return eventMapper.toDto(eventEntity);
            else
                throw new NotFoundException("Event Not Found");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public List<EventTypeCountDTO> countAllByStatus(EventStatus status) throws NotFoundException {
        log.debug("Request to count all event by status : {}", status);
        Optional<UserEntity> userEntity = userRepository.findById(securityUtils.getCurrentUserId());
        if (userEntity.isPresent()) {
            return eventRepository.countAllByStatus(userEntity.get(), status);
        } else {
            throw new NotFoundException("user Not Found");
        }
    }

    @Override
    public List<EventDTO> findByOwnerId(EventFilterVM requestParam) throws NotFoundException {
        log.debug("Request to find events by ownerId");
        if (requestParam == null)
            requestParam = new EventFilterVM();
        requestParam.setOwnerId(securityUtils.getCurrentUserId());
        return eventRepository
            .findAll(specification.getFilter(requestParam))
            .stream()
            .map(eventMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> findByOwnerIdAndDate(ZonedDateTime startDate, ZonedDateTime endDate) throws NotFoundException {
        log.debug("Request to find events by dates : {}, {}", startDate, endDate);
        return eventRepository
            .findAllByOwner_IdAndCreatedDateBetween(securityUtils.getCurrentUserId(), startDate, endDate)
            .stream()
            .map(eventMapper::toDto)
            .collect(Collectors.toList());
    }
}
