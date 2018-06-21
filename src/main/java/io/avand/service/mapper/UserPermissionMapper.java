package io.avand.service.mapper;

import io.avand.domain.UserPermissionEntity;
import io.avand.service.dto.UserPermissionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserAuthorityMapper.class})
public interface UserPermissionMapper extends EntityMapper<UserPermissionDTO, UserPermissionEntity> {
}
