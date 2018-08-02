package io.avand.service.mapper;

import io.avand.domain.entity.jpa.SubscriptionHistoryEntity;
import io.avand.service.dto.SubscriptionHistoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface SubscriptionHistoryMapper extends EntityMapper<SubscriptionHistoryDTO, SubscriptionHistoryEntity> {
}
