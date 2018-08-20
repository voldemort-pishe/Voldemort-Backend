package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyPipelineEntity;
import io.avand.service.dto.CompanyPipelineDTO;
import io.avand.web.rest.vm.response.CompanyPipelineIncludeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CompanyPipelineMapper extends EntityMapper<CompanyPipelineDTO, CompanyPipelineEntity>,
    VmMapper<CompanyPipelineDTO, CompanyPipelineIncludeVM> {

    @Override
    @Mapping(source = "company.id", target = "companyId")
    CompanyPipelineDTO toDto(CompanyPipelineEntity entity);
}
