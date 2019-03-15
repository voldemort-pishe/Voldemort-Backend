package hr.pishe.service.impl;

import hr.pishe.domain.enumeration.PlanType;
import hr.pishe.repository.jpa.PlanRepository;
import hr.pishe.service.PlanService;
import hr.pishe.service.dto.PlanDTO;
import hr.pishe.service.mapper.PlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<PlanDTO> findOneById(Long planId) {
        logger.debug("Request for service to get a plan by id : {}", planId);
        return planRepository.findById(planId).map(planMapper::toDto);
    }

    @Override
    public Optional<PlanDTO> findFreePlan() {
        logger.debug("Request to find free plan");
        return planRepository.findFirstByType(PlanType.FREE)
            .map(planMapper::toDto);
    }

    @Override
    public Page<PlanDTO> findAllByType(Pageable pageable, PlanType type) {
        logger.debug("Request for service to get all of plans");
        return planRepository.findAllByType(pageable, type).map(planMapper::toDto);
    }

    @Override
    public Page<PlanDTO> findActiveByType(Pageable pageable, PlanType type) {
        logger.debug("Request for service to get active plans");
        return planRepository.findAllByActiveIsTrueAndType(pageable, type)
            .map(planMapper::toDto);
    }

    @Override
    public void delete(Long planId) {
        logger.debug("Request for service to delete a plan : {}", planId);
        Optional<PlanDTO> planDTO = this.findOneById(planId);
        if (planDTO.isPresent()) {
            planRepository.delete(planId);
        }
    }
}
