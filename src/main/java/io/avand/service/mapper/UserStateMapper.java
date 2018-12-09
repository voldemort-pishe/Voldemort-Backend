package io.avand.service.mapper;

import io.avand.domain.entity.jpa.UserStateEntity;
import io.avand.service.dto.UserStateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStateMapper extends EntityMapper<UserStateDTO,UserStateEntity> {
}
