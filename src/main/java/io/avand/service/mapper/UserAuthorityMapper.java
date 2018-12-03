package io.avand.service.mapper;

import io.avand.domain.entity.jpa.UserAuthorityEntity;
import io.avand.service.dto.UserAuthorityDTO;
import io.avand.web.rest.vm.UserAuthorityVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAuthorityMapper extends EntityMapper<UserAuthorityDTO, UserAuthorityEntity>,
    VmMapper<UserAuthorityDTO, UserAuthorityVM> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "authority.id", target = "authorityId")
    UserAuthorityDTO toDto(UserAuthorityEntity entity);
}
