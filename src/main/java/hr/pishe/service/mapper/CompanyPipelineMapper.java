package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CompanyPipelineEntity;
import hr.pishe.service.dto.CompanyPipelineDTO;
import hr.pishe.web.rest.vm.response.CompanyPipelineIncludeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CompanyPipelineMapper extends EntityMapper<CompanyPipelineDTO, CompanyPipelineEntity>,
    VmMapper<CompanyPipelineDTO, CompanyPipelineIncludeVM> {

    @Override
    @Mapping(source = "company.id", target = "companyId")
    CompanyPipelineDTO toDto(CompanyPipelineEntity entity);
}
