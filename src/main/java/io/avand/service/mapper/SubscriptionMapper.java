package io.avand.service.mapper;

import io.avand.domain.entity.jpa.SubscriptionEntity;
import io.avand.service.dto.SubscriptionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface SubscriptionMapper extends EntityMapper<SubscriptionDTO, SubscriptionEntity> {
}
