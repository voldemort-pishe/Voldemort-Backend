package io.avand.service.mapper;

import io.avand.domain.CompanyEntity;
import io.avand.domain.EvaluationCriteriaEntity;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.EvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {})
public interface EvaluationCriteriaMapper extends EntityMapper<EvaluationCriteriaDTO, EvaluationCriteriaEntity> {
}
