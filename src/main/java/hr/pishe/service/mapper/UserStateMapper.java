package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.UserStateEntity;
import hr.pishe.service.dto.UserStateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStateMapper extends EntityMapper<UserStateDTO,UserStateEntity> {
}
