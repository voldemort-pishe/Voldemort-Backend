package io.avand.service.mapper;

import io.avand.domain.PlanEntity;
import io.avand.service.dto.PlanDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PlanMapper extends EntityMapper<PlanDTO, PlanEntity> {
}
