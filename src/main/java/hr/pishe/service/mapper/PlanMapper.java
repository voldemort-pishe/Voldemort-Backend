package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.PlanEntity;
import hr.pishe.service.dto.PlanDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PlanConfigMapper.class})
public interface PlanMapper extends EntityMapper<PlanDTO, PlanEntity> {
}
