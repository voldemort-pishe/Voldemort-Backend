package hr.pishe.service;

import hr.pishe.service.dto.CompanyContactDTO;
import javassist.NotFoundException;

public interface CompanyContactService {
    CompanyContactDTO save(CompanyContactDTO companyContactDTO);

    CompanyContactDTO findById(Long id);

    void delete(Long id) throws NotFoundException;
}
