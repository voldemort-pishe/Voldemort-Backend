package io.avand.service;

import io.avand.service.dto.CompanyContactDTO;
import javassist.NotFoundException;

public interface CompanyContactService {
    CompanyContactDTO save(CompanyContactDTO companyContactDTO);

    CompanyContactDTO findById(Long id);

    void delete(Long id) throws NotFoundException;
}
