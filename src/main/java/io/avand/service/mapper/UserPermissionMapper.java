package io.avand.service.mapper;

import io.avand.domain.entity.jpa.UserPermissionEntity;
import io.avand.service.dto.UserPermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface UserPermissionMapper extends EntityMapper<UserPermissionDTO, UserPermissionEntity> {

    @Override
    @Mapping(source = "userAuthority.id",target = "userAuthorityId")
    UserPermissionDTO toDto(UserPermissionEntity entity);
}
