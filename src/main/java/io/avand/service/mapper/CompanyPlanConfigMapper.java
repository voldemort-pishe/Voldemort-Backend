package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyPlanConfigEntity;
import io.avand.service.dto.CompanyPlanConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyPlanConfigMapper extends EntityMapper<CompanyPlanConfigDTO, CompanyPlanConfigEntity> {

    @Override
    @Mapping(source = "plan.id", target = "planId")
    CompanyPlanConfigDTO toDto(CompanyPlanConfigEntity entity);
}
