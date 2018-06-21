package io.avand.service.mapper;

import io.avand.domain.CandidateEvaluationCriteriaEntity;
import io.avand.service.dto.CandidateEvaluationCriteriaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface CandidateEvaluationCriteriaMapper extends EntityMapper<CandidateEvaluationCriteriaDTO, CandidateEvaluationCriteriaEntity> {
}
