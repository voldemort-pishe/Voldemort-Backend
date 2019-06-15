package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CompanyContactEntity;
import hr.pishe.service.dto.CompanyContactDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyContactMapper extends EntityMapper<CompanyContactDTO, CompanyContactEntity> {
}
