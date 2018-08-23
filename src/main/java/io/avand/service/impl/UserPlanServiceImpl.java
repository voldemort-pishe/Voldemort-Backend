package io.avand.service.impl;

import io.avand.domain.entity.jpa.*;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.repository.jpa.InvoiceRepository;
import io.avand.repository.jpa.PlanRepository;
import io.avand.repository.jpa.UserPlanRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.UserPlanService;
import io.avand.service.dto.UserPlanDTO;
import io.avand.service.mapper.UserPlanMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserPlanServiceImpl implements UserPlanService {

    private final Logger log = LoggerFactory.getLogger(UserPlanServiceImpl.class);
    private final UserPlanRepository userPlanRepository;
    private final UserPlanMapper userPlanMapper;
    private final PlanRepository planRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public UserPlanServiceImpl(UserPlanRepository userPlanRepository,
                               UserPlanMapper userPlanMapper,
                               PlanRepository planRepository,
                               InvoiceRepository invoiceRepository,
                               UserRepository userRepository) {
        this.userPlanRepository = userPlanRepository;
        this.userPlanMapper = userPlanMapper;
        this.planRepository = planRepository;
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserPlanDTO save(UserPlanDTO planDTO) {
        log.debug("Request to save userPlan : {}", planDTO);
        UserPlanEntity userPlanEntity = userPlanMapper.toEntity(planDTO);
        userPlanEntity = userPlanRepository.save(userPlanEntity);
        return userPlanMapper.toDto(userPlanEntity);
    }

    @Override
    public UserPlanDTO save(Long planId, Long invoiceId) throws NotFoundException {
        log.debug("Request to save userPlan by planId and invoiceId : {}, {}", planId, invoiceId);
        PlanEntity planEntity = planRepository.findOne(planId);
        if (planEntity != null) {
            Optional<UserEntity> userEntityOptional = userRepository.findByLogin(SecurityUtils.getCurrentUserLogin().get());
            if (userEntityOptional.isPresent()) {
                Optional<UserPlanEntity> userPlanEntityOptional = userPlanRepository.findByUser_Id(userEntityOptional.get().getId());
                UserPlanEntity userPlanEntity;
                InvoiceEntity oldInvoice = null;
                if (userPlanEntityOptional.isPresent()) {
                    userPlanEntity = userPlanEntityOptional.get();
                    oldInvoice = userPlanEntity.getInvoice();
                } else {
                    userPlanEntity = new UserPlanEntity();
                }
                userPlanEntity.setLength(planEntity.getLength());
                userPlanEntity.setUser(userEntityOptional.get());

                InvoiceEntity invoiceEntity = invoiceRepository.findOne(invoiceId);
                if (invoiceEntity != null) {
                    userPlanEntity.setInvoice(invoiceEntity);
                } else {
                    throw new NotFoundException("Invoice Not Available");
                }


                Set<UserPlanConfigEntity> userPlanConfigEntities = userPlanEntity.getPlanConfig();
                userPlanConfigEntities.clear();
                for (PlanConfigEntity planConfigEntity : planEntity.getPlanConfig()) {
                    UserPlanConfigEntity userPlanConfigEntity = new UserPlanConfigEntity();
                    userPlanConfigEntity.setType(planConfigEntity.getType());
                    userPlanConfigEntity.setValue(planConfigEntity.getValue());
                    userPlanConfigEntity.setPlan(userPlanEntity);
                    userPlanConfigEntities.add(userPlanConfigEntity);
                }
                userPlanEntity.setPlanConfig(userPlanConfigEntities);

                userPlanEntity = userPlanRepository.save(userPlanEntity);

                if (oldInvoice != null && oldInvoice.getStatus() == InvoiceStatus.INITIALIZED)
                    invoiceRepository.delete(oldInvoice);

                return userPlanMapper.toDto(userPlanEntity);
            } else {
                throw new NotFoundException("User Not Available");
            }
        } else {
            throw new NotFoundException("Plan Not Available");
        }
    }

    @Override
    public Optional<UserPlanDTO> findById(Long planId) {
        log.debug("Request to find userPlan by id : {}", planId);
        return userPlanRepository
            .findById(planId)
            .map(userPlanMapper::toDto);
    }

    @Override
    public Optional<UserPlanDTO> findByUserId(Long userId) {
        log.debug("Request to find userPlan by userId : {}", userId);
        return userPlanRepository
            .findByUser_Id(userId)
            .map(userPlanMapper::toDto);
    }

    @Override
    public Optional<UserPlanDTO> findByInvoiceId(Long invoiceId) {
        log.debug("Request to find userPlan by invoiceId : {}", invoiceId);
        return userPlanRepository
            .findByInvoice_Id(invoiceId)
            .map(userPlanMapper::toDto);
    }

    @Override
    public void delete(Long planId) {
        log.debug("Request to delete userPlan by id : {}", planId);
        userPlanRepository.delete(planId);
    }
}
