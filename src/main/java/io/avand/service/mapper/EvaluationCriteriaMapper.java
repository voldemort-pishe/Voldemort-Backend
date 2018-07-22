package io.avand.service.mapper;

import io.avand.domain.entity.jpa.EvaluationCriteriaEntity;
import io.avand.service.dto.EvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface EvaluationCriteriaMapper extends EntityMapper<EvaluationCriteriaDTO, EvaluationCriteriaEntity> {

    @Override
    @Mapping(source = "company.id",target = "companyId")
    EvaluationCriteriaDTO toDto(EvaluationCriteriaEntity entity);
}
