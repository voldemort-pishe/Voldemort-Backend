package io.avand.service;

import io.avand.service.dto.CompanyDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CompanyService {

    CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException;

    CompanyDTO findById(Long id) throws NotFoundException;

    List<CompanyDTO> findAll();

    void delete(Long id);

}
