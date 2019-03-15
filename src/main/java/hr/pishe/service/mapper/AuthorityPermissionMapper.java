package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.AuthorityEntity;
import hr.pishe.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface AuthorityPermissionMapper extends EntityMapper<AuthorityDTO, AuthorityEntity> {
    @Override
    @Mapping(target = "permissions",ignore = true)
    AuthorityDTO toDto(AuthorityEntity entity);

    @Override
    @Mapping(target = "permissions",ignore = true)
    AuthorityEntity toEntity(AuthorityDTO dto);
}
