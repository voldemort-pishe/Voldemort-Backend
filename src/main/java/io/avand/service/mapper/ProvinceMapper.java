package io.avand.service.mapper;

import io.avand.domain.entity.file.ProvinceEntity;
import io.avand.service.dto.ProvinceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface ProvinceMapper extends EntityMapper<ProvinceDTO, ProvinceEntity> {
}
