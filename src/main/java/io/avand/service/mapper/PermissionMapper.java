package io.avand.service.mapper;

import io.avand.domain.entity.jpa.PermissionEntity;
import io.avand.service.dto.PermissionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends EntityMapper<PermissionDTO, PermissionEntity> {
}
