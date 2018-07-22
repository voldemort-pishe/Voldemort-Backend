package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyPipelineEntity;
import io.avand.service.dto.CompanyPipelineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface CompanyPipelineMapper extends EntityMapper<CompanyPipelineDTO, CompanyPipelineEntity> {

    @Override
    @Mapping(source = "company.id",target = "companyId")
    CompanyPipelineDTO toDto(CompanyPipelineEntity entity);
}
