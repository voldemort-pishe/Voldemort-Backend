package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyContactEntity;
import io.avand.service.dto.CompanyContactDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyContactMapper extends EntityMapper<CompanyContactDTO, CompanyContactEntity> {
}
