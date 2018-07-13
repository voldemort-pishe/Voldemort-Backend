package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.service.dto.CandidateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    uses = {FeedbackMapper.class,
        CommentMapper.class,
        CandidateEvaluationCriteriaMapper.class,
        CandidateScheduleMapper.class,
        FileMapper.class})
public interface CandidateMapper extends EntityMapper<CandidateDTO, CandidateEntity> {
}
