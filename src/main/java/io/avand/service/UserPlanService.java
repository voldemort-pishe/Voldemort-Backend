package io.avand.service;

import io.avand.service.dto.PlanDTO;
import io.avand.service.dto.UserPlanDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserPlanService {

    UserPlanDTO save(UserPlanDTO planDTO);

    UserPlanDTO save(Long planId, Long invoiceId) throws NotFoundException;

    Optional<UserPlanDTO> findById(Long planId);

    Optional<UserPlanDTO> findByUserId(Long userId);

    Optional<UserPlanDTO> findByInvoiceId(Long invoiceId);

    void delete(Long planId);

}
