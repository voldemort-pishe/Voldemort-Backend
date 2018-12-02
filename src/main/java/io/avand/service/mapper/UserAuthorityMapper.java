package io.avand.service.mapper;

import io.avand.domain.entity.jpa.UserAuthorityEntity;
import io.avand.service.dto.UserAuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAuthorityMapper extends EntityMapper<UserAuthorityDTO, UserAuthorityEntity> {

    @Override
    @Mapping(source = "user.id",target = "userId")
    UserAuthorityDTO toDto(UserAuthorityEntity entity);
}
