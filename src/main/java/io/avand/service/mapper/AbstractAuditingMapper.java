package io.avand.service.mapper;

import io.avand.domain.AbstractAuditingEntity;
import io.avand.service.dto.AbstractAuditingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AbstractAuditingMapper extends EntityMapper<AbstractAuditingDTO, AbstractAuditingEntity> {
}
