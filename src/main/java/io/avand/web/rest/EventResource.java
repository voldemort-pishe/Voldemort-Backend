package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.EventService;
import io.avand.service.dto.EventDTO;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.EventDateVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);
    private final EventService eventService;

    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<EventDTO> findById(@PathVariable("id") Long id) {
        log.debug("REST Request to find event by id : {}", id);
        try {
            EventDTO eventDTO = eventService.findById(id);
            return new ResponseEntity<>(eventDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping
    @Timed
    public ResponseEntity<List<EventDTO>> findAllByOwner() {
        log.debug("Request to findAll event by owner");
        try {
            List<EventDTO> byOwnerId = eventService.findByOwnerId();
            return new ResponseEntity<>(byOwnerId, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/date")
    @Timed
    public ResponseEntity<List<EventDTO>> findAllByOwnerAndDate(@RequestBody EventDateVM eventDateVM) {
        log.debug("REST Request to findAll event by owner and date : {}", eventDateVM);
        try {
            List<EventDTO> byOwnerIdAndDate = eventService.findByOwnerIdAndDate(eventDateVM.getStartDate(), eventDateVM.getEndDate());
            return new ResponseEntity<>(byOwnerIdAndDate, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
