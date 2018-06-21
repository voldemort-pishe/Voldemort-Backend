package io.avand.service.mapper;

import io.avand.domain.CompanyEntity;
import io.avand.service.dto.CompanyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
    JobMapper.class,
    EvaluationCriteriaMapper.class,
    CompanyPipelineMapper.class,
    UserMapper.class}
    )
public interface CompanyMapper extends EntityMapper<CompanyDTO, CompanyEntity> {
}
