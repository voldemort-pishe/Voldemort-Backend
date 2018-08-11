package io.avand.service.impl;

import io.avand.domain.entity.jpa.SubscriptionEntity;
import io.avand.repository.jpa.SubscriptionRepository;
import io.avand.service.SubscriptionService;
import io.avand.service.dto.SubscriptionDTO;
import io.avand.service.mapper.SubscriptionMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public SubscriptionDTO save(SubscriptionDTO subscriptionDTO) {
        logger.debug("Request for service to save a subscription : {}", subscriptionDTO);
        return subscriptionMapper.toDto(subscriptionRepository.save(subscriptionMapper.toEntity(subscriptionDTO)));
    }

    @Override
    public SubscriptionDTO update(SubscriptionDTO subscriptionDTO) {
        return null;
    }

    @Override
    public SubscriptionDTO checkSubscription(Long userId) throws NotFoundException {
        logger.debug("Request to check user subscription : {}", userId);
        SubscriptionEntity subscriptionEntity = subscriptionRepository
            .findByStartDateBeforeAndEndDateAfterAndUserId(ZonedDateTime.now(), ZonedDateTime.now(), userId);
        if (subscriptionEntity != null) {
            return subscriptionMapper.toDto(subscriptionEntity);
        } else {
            throw new NotFoundException("Subscription Not Available");
        }

    }
}
