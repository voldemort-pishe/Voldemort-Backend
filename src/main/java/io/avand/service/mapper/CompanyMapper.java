package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.service.dto.CompanyDTO;
import io.avand.web.rest.vm.response.CompanyIncludeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {JobMapper.class, EvaluationCriteriaMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, CompanyEntity>, VmMapper<CompanyDTO, CompanyIncludeVM> {

    @Override
    @Mapping(source = "file.id", target = "fileId")
    @Mapping(source = "user.id", target = "userId")
    CompanyDTO toDto(CompanyEntity entity);
}
