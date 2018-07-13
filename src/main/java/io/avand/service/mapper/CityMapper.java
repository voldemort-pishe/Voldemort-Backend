package io.avand.service.mapper;

import io.avand.domain.entity.file.CityEntity;
import io.avand.service.dto.CityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, CityEntity> {
}
