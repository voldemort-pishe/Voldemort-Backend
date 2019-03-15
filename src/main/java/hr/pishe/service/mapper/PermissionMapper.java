package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.PermissionEntity;
import hr.pishe.service.dto.PermissionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AuthorityPermissionMapper.class)
public interface PermissionMapper extends EntityMapper<PermissionDTO, PermissionEntity> {
}
