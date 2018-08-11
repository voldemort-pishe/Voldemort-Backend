package io.avand.service;

import io.avand.service.dto.CompanyDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException;

    CompanyDTO findById(Long id) throws NotFoundException;

    CompanyDTO findBySubDomain(String subDomain) throws NotFoundException;

    Page<CompanyDTO> findAll(Pageable pageable) throws NotFoundException;

    List<CompanyDTO> findAll() throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
