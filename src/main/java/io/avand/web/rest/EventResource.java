package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.dto.EventDTO;
import io.avand.web.rest.component.EventComponent;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.EventDateVM;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);
    private final EventComponent eventComponent;

    public EventResource(EventComponent eventComponent) {
        this.eventComponent = eventComponent;
    }


    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<ResponseVM<EventDTO>> findById(@PathVariable("id") Long id) {
        log.debug("REST Request to find event by id : {}", id);
        try {
            ResponseVM<EventDTO> eventDTO = eventComponent.findById(id);
            return new ResponseEntity<>(eventDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping
    @Timed
    public ResponseEntity<List<ResponseVM<EventDTO>>> findAllByOwner(@RequestParam Map<String, String> requestParam) {
        log.debug("Request to findAll event by owner");
        try {
            List<ResponseVM<EventDTO>> byOwnerId = eventComponent.findByOwnerId(requestParam);
            return new ResponseEntity<>(byOwnerId, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/date")
    @Timed
    public ResponseEntity<List<ResponseVM<EventDTO>>> findAllByOwnerAndDate(@RequestBody EventDateVM eventDateVM) {
        log.debug("REST Request to findAll event by owner and date : {}", eventDateVM);
        try {
            List<ResponseVM<EventDTO>> byOwnerIdAndDate = eventComponent.findByOwnerIdAndDate(eventDateVM.getStartDate(), eventDateVM.getEndDate());
            return new ResponseEntity<>(byOwnerIdAndDate, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
