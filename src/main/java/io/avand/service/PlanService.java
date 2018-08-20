package io.avand.service;

import io.avand.service.dto.PlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    PlanDTO save(PlanDTO planDTO);

    Optional<PlanDTO> findOneById(Long planId);

    Page<PlanDTO> findAll(Pageable pageable);

    Page<PlanDTO> getActivePlans(Pageable pageable);

    void delete(Long planId);
}
