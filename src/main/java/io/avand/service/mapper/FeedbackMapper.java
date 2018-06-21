package io.avand.service.mapper;

import io.avand.domain.FeedbackEntity;
import io.avand.service.dto.FeedbackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, FeedbackEntity> {
}
