package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.service.dto.CandidateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
    FeedbackMapper.class,
    CommentMapper.class,
    CandidateScheduleMapper.class,
    CandidateEvaluationCriteriaMapper.class,
    FileMapper.class,
    JobMapper.class}
    )
public interface CandidateMapper extends EntityMapper<CandidateDTO, CandidateEntity> {
}
