package io.avand.service.mapper;

import io.avand.domain.entity.jpa.JobEntity;
import io.avand.service.dto.JobDTO;
import io.avand.web.rest.vm.response.JobIncludedVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface JobMapper extends EntityMapper<JobDTO, JobEntity>, VmMapper<JobDTO, JobIncludedVM> {

    @Override
    @Mapping(source = "company.id", target = "companyId")
    JobDTO toDto(JobEntity entity);
}
