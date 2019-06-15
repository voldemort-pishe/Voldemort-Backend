package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CompanyPlanEntity;
import hr.pishe.service.dto.CompanyPlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompanyPlanConfigMapper.class})
public interface CompanyPlanMapper extends EntityMapper<CompanyPlanDTO, CompanyPlanEntity> {

    @Override
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    CompanyPlanDTO toDto(CompanyPlanEntity entity);
}
