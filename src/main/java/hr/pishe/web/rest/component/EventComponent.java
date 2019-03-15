package hr.pishe.web.rest.component;

import hr.pishe.service.dto.EventDTO;
import hr.pishe.web.rest.vm.EventFilterVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventComponent {
    ResponseVM<EventDTO> findById(Long id) throws NotFoundException;

    List<ResponseVM<EventDTO>> findByOwnerId(EventFilterVM requestParam) throws NotFoundException;

    List<ResponseVM<EventDTO>> findByOwnerIdAndDate(ZonedDateTime start, ZonedDateTime end) throws NotFoundException;
}
