package hr.pishe.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.pishe.domain.enumeration.EventStatus;
import hr.pishe.service.EventService;
import hr.pishe.service.dto.EventDTO;
import hr.pishe.service.dto.EventTypeCountDTO;
import hr.pishe.web.rest.component.EventComponent;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.vm.EventDateVM;
import hr.pishe.web.rest.vm.EventFilterVM;
import hr.pishe.web.rest.vm.EventTypeCountVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
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
    private final EventComponent eventComponent;
    private final EventService eventService;

    public EventResource(EventComponent eventComponent,
                         EventService eventService) {
        this.eventComponent = eventComponent;
        this.eventService = eventService;
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
    public ResponseEntity<List<ResponseVM<EventDTO>>> findAllByOwner(EventFilterVM requestParam) {
        log.debug("Request to findAll event by owner");
        try {
            List<ResponseVM<EventDTO>> byOwnerId = eventComponent.findByOwnerId(requestParam);
            return new ResponseEntity<>(byOwnerId, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/count")
    @Timed
    public ResponseEntity<EventTypeCountVM> countAllByStatus(@RequestParam EventStatus status) {
        log.debug("REST Request to count all event by status");
        try {
            List<EventTypeCountDTO> hashMap = eventService.countAllByStatus(status);
            Long count = 0L;
            for (EventTypeCountDTO eventTypeCountDTO : hashMap) {
                count += eventTypeCountDTO.getCount();
            }
            EventTypeCountVM eventTypeCountVM = new EventTypeCountVM();
            eventTypeCountVM.setCount(count);
            eventTypeCountVM.setItems(hashMap);

            return new ResponseEntity<>(eventTypeCountVM, HttpStatus.OK);
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
