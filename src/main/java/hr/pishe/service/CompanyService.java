package hr.pishe.service;

import hr.pishe.service.dto.CompanyDTO;
import javassist.NotFoundException;

public interface CompanyService {

    CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException;

    CompanyDTO update(CompanyDTO companyDTO) throws NotFoundException;

    CompanyDTO findById(Long id) throws NotFoundException;

    CompanyDTO findBySubDomain(String subDomain) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
