package io.avand.service;

import io.avand.service.dto.SubscriptionDTO;

public interface SubscriptionService {

    SubscriptionDTO save(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO update(SubscriptionDTO subscriptionDTO);
}
