package io.avand.service.impl;

import io.avand.domain.entity.jpa.PlanEntity;
import io.avand.repository.jpa.PlanRepository;
import io.avand.service.PlanService;
import io.avand.service.dto.PlanDTO;
import io.avand.service.mapper.PlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    private Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

    private final PlanRepository planRepository;

    private final PlanMapper planMapper;

    public PlanServiceImpl(PlanRepository planRepository,
                           PlanMapper planMapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }

    @Override
    public PlanDTO save(PlanDTO planDTO) {
        logger.debug("Request for service to save a plan : {}", planDTO);
        return planMapper.toDto(planRepository.save(planMapper.toEntity(planDTO)));
    }

    @Override
    public PlanDTO update(PlanDTO planDTO) {
        logger.debug("Request for service to save a plan : {}", planDTO);
        PlanEntity planEntity = planRepository.findOne(planDTO.getId());
        planEntity = planMapper.toEntity(planDTO);
        return planMapper.toDto(planRepository.save(planEntity));
    }

    @Override
    public void delete(Long planId) {
        logger.debug("Request for service to delete a plan : {}", planId);
        Optional<PlanDTO> planDTO = this.findOneById(planId);
        if (planDTO.isPresent()) {
            planRepository.delete(planId);
        }
    }

    @Override
    public Optional<PlanDTO> findOneById(Long planId) {
        logger.debug("Request for service to get a plan by id : {}", planId);
        return planRepository.findById(planId).map(planMapper::toDto);
    }

    @Override
    public Optional<PlanDTO> findOneByTitle(String planTitle) {
        logger.debug("Request for service to get a plan by title : {}", planTitle);
        return planRepository.findByTitle(planTitle).map(planMapper::toDto);
    }

    @Override
    public Page<PlanDTO> findAll(Pageable pageable) {
        logger.debug("Request for service to get all of plans");
        return planRepository.findAll(pageable).map(planMapper::toDto);
    }

    @Override
    public Page<PlanDTO> getActivePlans(Pageable pageable) {
        logger.debug("Request for service to get active plans");
        return planRepository.findAllByActiveIsTrue(pageable)
            .map(planMapper::toDto);
    }
}
