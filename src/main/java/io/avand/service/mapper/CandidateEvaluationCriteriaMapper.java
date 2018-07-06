package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.CandidateEvaluationCriteriaEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CandidateEvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {})
public interface CandidateEvaluationCriteriaMapper extends EntityMapper<CandidateEvaluationCriteriaDTO, CandidateEvaluationCriteriaEntity> {
}
