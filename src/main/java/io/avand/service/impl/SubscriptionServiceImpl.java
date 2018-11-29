package io.avand.service.impl;

import io.avand.domain.entity.jpa.SubscriptionEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.entity.jpa.UserPlanEntity;
import io.avand.repository.jpa.SubscriptionRepository;
import io.avand.repository.jpa.UserPlanRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.service.SubscriptionService;
import io.avand.service.dto.SubscriptionDTO;
import io.avand.service.mapper.SubscriptionMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    private final UserRepository userRepository;

    private final UserPlanRepository userPlanRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   SubscriptionMapper subscriptionMapper,
                                   UserRepository userRepository,
                                   UserPlanRepository userPlanRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.userRepository = userRepository;
        this.userPlanRepository = userPlanRepository;
    }

    @Override
    public SubscriptionDTO save(SubscriptionDTO subscriptionDTO) throws NotFoundException {
        logger.debug("Request for service to save a subscription : {}", subscriptionDTO);
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByUser_Id(subscriptionDTO.getUserId());
        if (subscriptionEntity != null) {
            subscriptionEntity.setStartDate(subscriptionDTO.getStartDate());
            subscriptionEntity.setEndDate(subscriptionDTO.getEndDate());
        } else {
            subscriptionEntity = subscriptionMapper.toEntity(subscriptionDTO);
            Optional<UserEntity> userEntityOptional = userRepository.findById(subscriptionDTO.getUserId());
            if (userEntityOptional.isPresent()) {
                subscriptionEntity.setUser(userEntityOptional.get());
            } else {
                throw new NotFoundException("User Not Available");
            }
        }

        UserPlanEntity userPlanEntity = userPlanRepository.findOne(subscriptionDTO.getUserPlanId());
        subscriptionEntity.setUserPlan(userPlanEntity);

        subscriptionEntity = subscriptionRepository.save(subscriptionEntity);

        return subscriptionMapper.toDto(subscriptionEntity);
    }

    @Override
    public SubscriptionDTO checkSubscription(Long userId) throws NotFoundException {
        logger.debug("Request to check user subscription : {}", userId);
        SubscriptionEntity subscriptionEntity = subscriptionRepository
            .findByStartDateBeforeAndEndDateAfterAndUser_Id(ZonedDateTime.now(), ZonedDateTime.now(), userId);
        if (subscriptionEntity != null) {
            return subscriptionMapper.toDto(subscriptionEntity);
        } else {
            throw new NotFoundException("Subscription Not Available");
        }

    }
}
