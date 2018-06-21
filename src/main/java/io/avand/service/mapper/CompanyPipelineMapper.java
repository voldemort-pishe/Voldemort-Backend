package io.avand.service.mapper;

import io.avand.domain.CompanyPipelineEntity;
import io.avand.service.dto.CompanyPipelineDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface CompanyPipelineMapper extends EntityMapper<CompanyPipelineDTO, CompanyPipelineEntity> {
}
