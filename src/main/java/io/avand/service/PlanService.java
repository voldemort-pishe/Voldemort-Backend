package io.avand.service;

import io.avand.service.dto.PlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    PlanDTO save(PlanDTO planDTO);

    PlanDTO update(PlanDTO planDTO);

    void delete(Long planId);

    Optional<PlanDTO> findOneById(Long planId);

    Optional<PlanDTO> findOneByTitle(String planTitle);

    Page<PlanDTO> findAll(Pageable pageable);

    Page<PlanDTO> getActivePlans(Pageable pageable);
}
