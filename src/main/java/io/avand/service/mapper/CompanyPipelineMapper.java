package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyPipelineEntity;
import io.avand.service.dto.CompanyPipelineDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface CompanyPipelineMapper extends EntityMapper<CompanyPipelineDTO, CompanyPipelineEntity> {
}
