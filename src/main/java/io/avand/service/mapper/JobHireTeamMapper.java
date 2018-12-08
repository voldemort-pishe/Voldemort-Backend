package io.avand.service.mapper;

import io.avand.domain.entity.jpa.JobHireTeamEntity;
import io.avand.service.dto.JobHireTeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobHireTeamMapper extends EntityMapper<JobHireTeamDTO, JobHireTeamEntity> {
    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "job.id", target = "jobId")
    JobHireTeamDTO toDto(JobHireTeamEntity entity);
}
