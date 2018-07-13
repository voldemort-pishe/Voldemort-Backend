package io.avand.service;

import io.avand.service.dto.CompanyMemberDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CompanyMemberService {

    CompanyMemberDTO save(CompanyMemberDTO companyMemberDTO) throws NotFoundException;

    CompanyMemberDTO findById(Long id) throws NotFoundException;

    List<CompanyMemberDTO> findAll(Long companyId) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
