package hr.pishe.service.mapper;

import hr.pishe.domain.entity.file.ProvinceEntity;
import hr.pishe.service.dto.ProvinceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface ProvinceMapper extends EntityMapper<ProvinceDTO, ProvinceEntity> {
}
