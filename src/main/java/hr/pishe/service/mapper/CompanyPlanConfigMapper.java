package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CompanyPlanConfigEntity;
import hr.pishe.service.dto.CompanyPlanConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyPlanConfigMapper extends EntityMapper<CompanyPlanConfigDTO, CompanyPlanConfigEntity> {

    @Override
    @Mapping(source = "plan.id", target = "planId")
    CompanyPlanConfigDTO toDto(CompanyPlanConfigEntity entity);
}
