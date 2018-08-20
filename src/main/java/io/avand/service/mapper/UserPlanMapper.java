package io.avand.service.mapper;

import io.avand.domain.entity.jpa.PlanEntity;
import io.avand.domain.entity.jpa.UserPlanEntity;
import io.avand.service.dto.PlanDTO;
import io.avand.service.dto.UserPlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserPlanConfigMapper.class})
public interface UserPlanMapper extends EntityMapper<UserPlanDTO, UserPlanEntity> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    UserPlanDTO toDto(UserPlanEntity entity);
}
