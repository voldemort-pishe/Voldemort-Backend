package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.AuthorityEntity;
import hr.pishe.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PermissionAuthorityMapper.class})
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, AuthorityEntity> {
}
