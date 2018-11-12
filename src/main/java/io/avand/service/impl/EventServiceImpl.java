package io.avand.service.impl;

import io.avand.domain.entity.jpa.EventEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.enumeration.EventStatus;
import io.avand.repository.jpa.EventRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.EventService;
import io.avand.service.dto.EventDTO;
import io.avand.service.mapper.EventMapper;
import io.avand.web.specification.BaseSpecification;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final BaseSpecification<EventEntity> specification;

    public EventServiceImpl(EventRepository eventRepository,
                            EventMapper eventMapper,
                            SecurityUtils securityUtils,
                            UserRepository userRepository,
                            BaseSpecification<EventEntity> specification) {
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
    public List<EventDTO> findByOwnerId(Map<String, String> requestParam) throws NotFoundException {
        log.debug("Request to find events by ownerId");
        //TODO inja bayad avaz she ba eventRepository.findAllByOwner_Id()
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
