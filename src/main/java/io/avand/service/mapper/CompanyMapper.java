package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.service.dto.CompanyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {JobMapper.class,EvaluationCriteriaMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, CompanyEntity> {
}
