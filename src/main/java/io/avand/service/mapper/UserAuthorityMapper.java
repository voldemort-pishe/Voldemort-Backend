package io.avand.service.mapper;

import io.avand.domain.entity.jpa.UserAuthorityEntity;
import io.avand.service.dto.UserAuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {UserPermissionMapper.class})
public interface UserAuthorityMapper extends EntityMapper<UserAuthorityDTO, UserAuthorityEntity> {
}
