package io.avand.service.mapper;

import io.avand.domain.entity.jpa.PlanConfigEntity;
import io.avand.service.dto.PlanConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlanConfigMapper extends EntityMapper<PlanConfigDTO, PlanConfigEntity> {

    @Override
    @Mapping(source = "plan.id", target = "planId")
    PlanConfigDTO toDto(PlanConfigEntity entity);
}
