package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.JobEntity;
import hr.pishe.service.dto.JobDTO;
import hr.pishe.web.rest.vm.response.JobIncludedVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface JobMapper extends EntityMapper<JobDTO, JobEntity>, VmMapper<JobDTO, JobIncludedVM> {

    @Override
    @Mapping(source = "company.id", target = "companyId")
    JobDTO toDto(JobEntity entity);
}
