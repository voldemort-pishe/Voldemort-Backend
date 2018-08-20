package io.avand.service.impl;

import io.avand.domain.entity.jpa.PlanConfigEntity;
import io.avand.domain.entity.jpa.PlanEntity;
import io.avand.repository.jpa.PlanConfigRepository;
import io.avand.repository.jpa.PlanRepository;
import io.avand.service.PlanConfigService;
import io.avand.service.dto.PlanConfigDTO;
import io.avand.service.mapper.PlanConfigMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlanConfigServiceImpl implements PlanConfigService {

    private final Logger log = LoggerFactory.getLogger(PlanConfigServiceImpl.class);
    private final PlanConfigRepository planConfigRepository;
    private final PlanRepository planRepository;
    private final PlanConfigMapper planConfigMapper;

    public PlanConfigServiceImpl(PlanConfigRepository planConfigRepository,
                                 PlanRepository planRepository,
                                 PlanConfigMapper planConfigMapper) {
        this.planConfigRepository = planConfigRepository;
        this.planRepository = planRepository;
        this.planConfigMapper = planConfigMapper;
    }


    @Override
    public PlanConfigDTO save(PlanConfigDTO planConfigDTO) throws NotFoundException {
        log.debug("Request to save planConfig : {}", planConfigDTO);
        PlanEntity planEntity = planRepository.findOne(planConfigDTO.getPlanId());
        if (planEntity != null) {
            PlanConfigEntity planConfigEntity = planConfigMapper.toEntity(planConfigDTO);
            planConfigEntity.setPlan(planEntity);
            planConfigEntity = planConfigRepository.save(planConfigEntity);
            return planConfigMapper.toDto(planConfigEntity);
        } else {
            throw new NotFoundException("Plan Not Found");
        }
    }

    @Override
    public PlanConfigDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find planConfig by id : {}", id);
        PlanConfigEntity planConfigEntity = planConfigRepository.findOne(id);
        if (planConfigEntity != null) {
            return planConfigMapper.toDto(planConfigEntity);
        } else {
            throw new NotFoundException("Plan Config Not Found");
        }
    }

    @Override
    public Page<PlanConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to findAll planConfig");
        return planConfigRepository
            .findAll(pageable)
            .map(planConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete planConfig by id : {}", id);
        PlanConfigEntity planConfigEntity = planConfigRepository.findOne(id);
        if (planConfigEntity != null) {
            planConfigRepository.delete(planConfigEntity);
        } else {
            throw new NotFoundException("Plan Config Not Found");
        }
    }
}
