package io.avand.service;

import io.avand.domain.enumeration.PlanType;
import io.avand.service.dto.PlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    PlanDTO save(PlanDTO planDTO);

    Optional<PlanDTO> findOneById(Long planId);

    Optional<PlanDTO> findFreePlan();

    Page<PlanDTO> findAllByType(Pageable pageable, PlanType type);

    Page<PlanDTO> findActiveByType(Pageable pageable, PlanType type);

    void delete(Long planId);
}
