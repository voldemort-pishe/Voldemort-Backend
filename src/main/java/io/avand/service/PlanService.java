package io.avand.service;

import io.avand.service.dto.PlanDTO;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    Optional<PlanDTO> findOneById(Long planId);

    Optional<PlanDTO> findOneByTitle(String planTitle);

    List<PlanDTO> getActivePlans();
}
