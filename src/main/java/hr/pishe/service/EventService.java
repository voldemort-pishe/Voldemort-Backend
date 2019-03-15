package hr.pishe.service;

import hr.pishe.domain.enumeration.EventStatus;
import hr.pishe.service.dto.EventDTO;
import hr.pishe.service.dto.EventTypeCountDTO;
import hr.pishe.web.rest.vm.EventFilterVM;
import javassist.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventService {

    EventDTO save(EventDTO eventDTO) throws NotFoundException;

    EventDTO findById(Long id) throws NotFoundException;

    List<EventTypeCountDTO> countAllByStatus(EventStatus status) throws NotFoundException;

    List<EventDTO> findByOwnerId(EventFilterVM request) throws NotFoundException;

    List<EventDTO> findByOwnerIdAndDate(ZonedDateTime startDate,ZonedDateTime endDate) throws NotFoundException;

}
