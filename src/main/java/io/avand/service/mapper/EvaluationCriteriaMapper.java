package io.avand.service.mapper;

import io.avand.domain.EvaluationCriteriaEntity;
import io.avand.service.dto.EvaluationCriteriaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface EvaluationCriteriaMapper extends EntityMapper<EvaluationCriteriaDTO, EvaluationCriteriaEntity> {
}
