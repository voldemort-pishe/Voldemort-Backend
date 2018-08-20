package io.avand.service.mapper;

import io.avand.domain.entity.jpa.SubscriptionEntity;
import io.avand.service.dto.SubscriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface SubscriptionMapper extends EntityMapper<SubscriptionDTO, SubscriptionEntity> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "plan.id", target = "planId")
    SubscriptionDTO toDto(SubscriptionEntity entity);
}
