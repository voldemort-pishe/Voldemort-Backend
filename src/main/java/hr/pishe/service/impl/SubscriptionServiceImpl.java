package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.CompanyEntity;
import hr.pishe.domain.entity.jpa.CompanyPlanEntity;
import hr.pishe.domain.entity.jpa.SubscriptionEntity;
import hr.pishe.repository.jpa.CompanyPlanRepository;
import hr.pishe.repository.jpa.CompanyRepository;
import hr.pishe.repository.jpa.SubscriptionRepository;
import hr.pishe.service.SubscriptionService;
import hr.pishe.service.dto.SubscriptionDTO;
import hr.pishe.service.mapper.SubscriptionMapper;
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

    private final CompanyRepository companyRepository;

    private final CompanyPlanRepository companyPlanRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   SubscriptionMapper subscriptionMapper,
                                   CompanyRepository companyRepository,
                                   CompanyPlanRepository companyPlanRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.companyRepository = companyRepository;
        this.companyPlanRepository = companyPlanRepository;
    }

    @Override
    public SubscriptionDTO save(Long planId, Long companyId) throws NotFoundException {
        logger.debug("Request to save subscription by planId and companyId");
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByCompany_Id(companyId);
        if (subscriptionEntity == null) {
            subscriptionEntity = new SubscriptionEntity();
            Optional<CompanyEntity> companyEntityOptional = companyRepository.findById(companyId);
            if (companyEntityOptional.isPresent()) {
                subscriptionEntity.setCompany(companyEntityOptional.get());
            } else {
                throw new NotFoundException("Company Not Available");
            }
        }

        CompanyPlanEntity companyPlanEntity = companyPlanRepository.findOne(planId);
        subscriptionEntity.setCompanyPlan(companyPlanEntity);
        subscriptionEntity.setStartDate(ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
        subscriptionEntity.setActualDate(subscriptionEntity.getStartDate().plusDays(companyPlanEntity.getLength() + 1));
        subscriptionEntity.setEndDate(subscriptionEntity.getActualDate().plusDays(companyPlanEntity.getExtraLength()));
        subscriptionEntity = subscriptionRepository.save(subscriptionEntity);

        return subscriptionMapper.toDto(subscriptionEntity);
    }

    @Override
    public SubscriptionDTO checkSubscription(Long companyId) throws NotFoundException {
        logger.debug("Request to check user subscription : {}", companyId);
        SubscriptionEntity subscriptionEntity = subscriptionRepository
            .findByStartDateBeforeAndEndDateAfterAndCompany_Id(ZonedDateTime.now(), ZonedDateTime.now(), companyId);
        if (subscriptionEntity != null) {
            return subscriptionMapper.toDto(subscriptionEntity);
        } else {
            throw new NotFoundException("Subscription Not Available");
        }

    }
}
