package io.avand.service.mapper;

import io.avand.domain.UserAuthorityEntity;
import io.avand.service.dto.UserAuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserPermissionMapper.class, UserMapper.class})
public interface UserAuthorityMapper extends EntityMapper<UserAuthorityDTO, UserAuthorityEntity> {
}
