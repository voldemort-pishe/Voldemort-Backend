package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.JobEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
    uses = {FeedbackMapper.class,
        CommentMapper.class,
        CandidateEvaluationCriteriaMapper.class,
        CandidateScheduleMapper.class,
        FileMapper.class})
public interface CandidateMapper extends EntityMapper<CandidateDTO, CandidateEntity> {
}
