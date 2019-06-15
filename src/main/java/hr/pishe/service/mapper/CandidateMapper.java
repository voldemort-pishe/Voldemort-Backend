package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CandidateEntity;
import hr.pishe.service.dto.CandidateDTO;
import hr.pishe.web.rest.vm.response.CandidateIncludeVM;
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
