package io.avand.service.mapper;

import io.avand.domain.entity.jpa.EvaluationCriteriaEntity;
import io.avand.service.dto.EvaluationCriteriaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface EvaluationCriteriaMapper extends EntityMapper<EvaluationCriteriaDTO, EvaluationCriteriaEntity> {
}
