package io.avand.service;

import io.avand.service.dto.PlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    Optional<PlanDTO> findOneById(Long planId);

    Optional<PlanDTO> findOneByTitle(String planTitle);

    Page<PlanDTO> getActivePlans(Pageable pageable);
}
