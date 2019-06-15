package hr.pishe.service;

import hr.pishe.service.dto.SubscriptionDTO;
import javassist.NotFoundException;

public interface SubscriptionService {

    SubscriptionDTO save(Long planId, Long companyId) throws NotFoundException;

    SubscriptionDTO checkSubscription(Long userId) throws NotFoundException;
}
