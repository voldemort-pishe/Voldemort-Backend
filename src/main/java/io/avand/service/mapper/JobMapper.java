package io.avand.service.mapper;

import io.avand.domain.entity.jpa.JobEntity;
import io.avand.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CandidateMapper.class})
public interface JobMapper extends EntityMapper<JobDTO, JobEntity> {

    @Override
    @Mapping(source = "company.id",target = "companyId")
    JobDTO toDto(JobEntity entity);
}
