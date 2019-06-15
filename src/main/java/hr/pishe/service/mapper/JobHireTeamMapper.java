package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.JobHireTeamEntity;
import hr.pishe.service.dto.JobHireTeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobHireTeamMapper extends EntityMapper<JobHireTeamDTO, JobHireTeamEntity> {
    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "job.id", target = "jobId")
    JobHireTeamDTO toDto(JobHireTeamEntity entity);
}
