package io.avand.service.mapper;

import io.avand.domain.entity.jpa.FeedbackEntity;
import io.avand.service.dto.FeedbackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, FeedbackEntity> {
}
