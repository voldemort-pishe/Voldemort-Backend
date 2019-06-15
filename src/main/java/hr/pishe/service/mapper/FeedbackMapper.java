package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.FeedbackEntity;
import hr.pishe.service.dto.FeedbackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, FeedbackEntity> {

    @Override
    @Mapping(source = "candidate.id", target = "candidateId")
    FeedbackDTO toDto(FeedbackEntity entity);
}
