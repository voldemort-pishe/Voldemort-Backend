package io.avand.service;

import io.avand.service.dto.EventDTO;
import javassist.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface EventService {

    EventDTO save(EventDTO eventDTO) throws NotFoundException;

    EventDTO findById(Long id) throws NotFoundException;

    List<EventDTO> findByOwnerId(Map<String,String> requestParam) throws NotFoundException;

    List<EventDTO> findByOwnerIdAndDate(ZonedDateTime startDate,ZonedDateTime endDate) throws NotFoundException;

}
