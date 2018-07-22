package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.service.dto.CandidateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
    uses = {FeedbackMapper.class,
        CommentMapper.class,
        CandidateEvaluationCriteriaMapper.class,
        CandidateScheduleMapper.class,
        FileMapper.class})
public interface CandidateMapper extends EntityMapper<CandidateDTO, CandidateEntity> {

    @Override
    @Mapping(source = "file.id",target = "fileId")
    @Mapping(source = "job.id",target = "jobId")
    CandidateDTO toDto(CandidateEntity entity);
}
