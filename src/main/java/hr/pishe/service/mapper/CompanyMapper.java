package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CompanyEntity;
import hr.pishe.service.dto.CompanyDTO;
import hr.pishe.web.rest.vm.response.CompanyIncludeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {JobMapper.class, EvaluationCriteriaMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, CompanyEntity>, VmMapper<CompanyDTO, CompanyIncludeVM> {

    @Override
    @Mapping(source = "file.id", target = "fileId")
    @Mapping(source = "user.id", target = "userId")
    CompanyDTO toDto(CompanyEntity entity);
}
