package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.EventEntity;
import hr.pishe.service.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, EventEntity> {

    @Override
    @Mapping(source = "owner.id", target = "ownerId")
    EventDTO toDto(EventEntity entity);
}
