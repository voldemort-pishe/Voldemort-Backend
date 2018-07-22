package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateEvaluationCriteriaEntity;
import io.avand.service.dto.CandidateEvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface CandidateEvaluationCriteriaMapper extends EntityMapper<CandidateEvaluationCriteriaDTO, CandidateEvaluationCriteriaEntity> {

    @Override
    @Mapping(source = "candidate.id",target = "candidateId")
    CandidateEvaluationCriteriaDTO toDto(CandidateEvaluationCriteriaEntity entity);
}
