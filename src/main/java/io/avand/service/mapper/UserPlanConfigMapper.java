package io.avand.service.mapper;

import io.avand.domain.entity.jpa.PlanConfigEntity;
import io.avand.domain.entity.jpa.UserPlanConfigEntity;
import io.avand.service.dto.PlanConfigDTO;
import io.avand.service.dto.UserPlanConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPlanConfigMapper extends EntityMapper<UserPlanConfigDTO, UserPlanConfigEntity> {

    @Override
    @Mapping(source = "plan.id", target = "planId")
    UserPlanConfigDTO toDto(UserPlanConfigEntity entity);
}
