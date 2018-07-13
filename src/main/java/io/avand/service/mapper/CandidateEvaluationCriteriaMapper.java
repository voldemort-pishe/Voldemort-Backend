package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateEvaluationCriteriaEntity;
import io.avand.service.dto.CandidateEvaluationCriteriaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface CandidateEvaluationCriteriaMapper extends EntityMapper<CandidateEvaluationCriteriaDTO, CandidateEvaluationCriteriaEntity> {
}
