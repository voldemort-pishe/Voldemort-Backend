package hr.pishe.service.mapper;

import hr.pishe.domain.entity.file.CityEntity;
import hr.pishe.service.dto.CityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, CityEntity> {
}
