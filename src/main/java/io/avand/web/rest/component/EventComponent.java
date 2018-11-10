package io.avand.web.rest.component;

import io.avand.service.dto.EventDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventComponent {
    ResponseVM<EventDTO> findById(Long id) throws NotFoundException;

    List<ResponseVM<EventDTO>> findByOwnerId() throws NotFoundException;

    List<ResponseVM<EventDTO>> findByOwnerIdAndDate(ZonedDateTime start, ZonedDateTime end) throws NotFoundException;
}
