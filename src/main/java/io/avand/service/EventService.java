package io.avand.service;

import io.avand.service.dto.EventDTO;
import javassist.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventService {

    EventDTO save(EventDTO eventDTO);

    EventDTO findById(Long id) throws NotFoundException;

    List<EventDTO> findByOwnerId() throws NotFoundException;

    List<EventDTO> findByOwnerIdAndDate(ZonedDateTime startDate,ZonedDateTime endDate) throws NotFoundException;

}
