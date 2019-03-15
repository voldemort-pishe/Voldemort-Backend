package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.SubscriptionEntity;
import hr.pishe.service.dto.SubscriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface SubscriptionMapper extends EntityMapper<SubscriptionDTO, SubscriptionEntity> {

    @Override
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "companyPlan.id", target = "companyPlanId")
    SubscriptionDTO toDto(SubscriptionEntity entity);
}
