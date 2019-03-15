package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.EvaluationCriteriaEntity;
import hr.pishe.service.dto.EvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface EvaluationCriteriaMapper extends EntityMapper<EvaluationCriteriaDTO, EvaluationCriteriaEntity> {

    @Override
    @Mapping(source = "company.id",target = "companyId")
    EvaluationCriteriaDTO toDto(EvaluationCriteriaEntity entity);
}
