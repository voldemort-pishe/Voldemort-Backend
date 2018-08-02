package io.avand.service;

import io.avand.service.dto.SubscriptionHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionHistoryService {

    SubscriptionHistoryDTO save(SubscriptionHistoryDTO subscriptionHistoryDTO);

    SubscriptionHistoryDTO update(SubscriptionHistoryDTO subscriptionHistoryDTO);

    void delete(Long id);

    Page<SubscriptionHistoryDTO> findAll(Pageable pageable);
}
