package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.UserAuthorityEntity;
import hr.pishe.service.dto.UserAuthorityDTO;
import hr.pishe.web.rest.vm.UserAuthorityVM;
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
