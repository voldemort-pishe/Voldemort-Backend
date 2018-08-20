package io.avand.service;

import io.avand.service.dto.PlanConfigDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PlanConfigService {

    PlanConfigDTO save(PlanConfigDTO planConfigDTO) throws NotFoundException;

    PlanConfigDTO findById(Long id) throws NotFoundException;

    Page<PlanConfigDTO> findAll(Pageable pageable);

    void delete(Long id) throws NotFoundException;
}
