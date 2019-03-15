package hr.pishe.aop.event;

import hr.pishe.service.EventService;
import hr.pishe.service.dto.EventDTO;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements ApplicationListener<CustomEvent> {

    private final Logger log = LoggerFactory.getLogger(EventListener.class);
    private final EventService eventService;

    public EventListener(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void onApplicationEvent(CustomEvent customEvent) {
        log.debug("An Event Published : {}", customEvent);
        EventDTO eventDTO = new EventDTO();
        eventDTO.setTitle(customEvent.getTitle());
        eventDTO.setDescription(customEvent.getDescription());
        eventDTO.setExtra(customEvent.getExtra());
        eventDTO.setType(customEvent.getType());
        eventDTO.setOwnerId(customEvent.getOwner());
        try {
            eventService.save(eventDTO);
        } catch (NotFoundException ignore) {
        }
    }
}
