package io.avand.service;

import io.avand.service.dto.CompanyPlanDTO;
import javassist.NotFoundException;

import java.util.Optional;

public interface CompanyPlanService {

    CompanyPlanDTO save(CompanyPlanDTO planDTO);

    CompanyPlanDTO save(Long planId, Long invoiceId, Long companyId) throws NotFoundException;

    Optional<CompanyPlanDTO> findById(Long planId);

    Optional<CompanyPlanDTO> findByCompanyId(Long companyId);

    Optional<CompanyPlanDTO> findByInvoiceId(Long invoiceId);

    void delete(Long planId);

}
