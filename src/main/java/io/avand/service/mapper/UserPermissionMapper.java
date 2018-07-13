package io.avand.service.mapper;

import io.avand.domain.entity.jpa.UserPermissionEntity;
import io.avand.service.dto.UserPermissionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface UserPermissionMapper extends EntityMapper<UserPermissionDTO, UserPermissionEntity> {
}
