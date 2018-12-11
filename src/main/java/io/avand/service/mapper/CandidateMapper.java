package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.web.rest.vm.response.CandidateIncludeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
    uses = {FeedbackMapper.class,
        CommentMapper.class,
        CandidateEvaluationCriteriaMapper.class,
        CandidateScheduleMapper.class,
        FileMapper.class})
public interface CandidateMapper extends EntityMapper<CandidateDTO, CandidateEntity>, VmMapper<CandidateDTO, CandidateIncludeVM> {

    @Override
    @Mapping(source = "file.id", target = "fileId")
    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "candidatePipeline.id", target = "candidatePipeline")
    CandidateDTO toDto(CandidateEntity entity);

    @Override
    @Mapping(target = "candidatePipeline", ignore = true)
    CandidateEntity toEntity(CandidateDTO dto);
}
