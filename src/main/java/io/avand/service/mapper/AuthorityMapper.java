package io.avand.service.mapper;

import io.avand.domain.AuthorityEntity;
import io.avand.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, AuthorityEntity> {
}
