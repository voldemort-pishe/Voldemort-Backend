package io.avand.service.mapper;

import io.avand.domain.entity.jpa.SubscriptionEntity;
import io.avand.service.dto.SubscriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface SubscriptionMapper extends EntityMapper<SubscriptionDTO, SubscriptionEntity> {

    @Override
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "companyPlan.id", target = "companyPlanId")
    SubscriptionDTO toDto(SubscriptionEntity entity);
}
