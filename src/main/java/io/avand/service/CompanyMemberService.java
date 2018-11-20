package io.avand.service;

import io.avand.service.dto.CompanyMemberDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyMemberService {

    List<CompanyMemberDTO> save(List<String> emails) throws NotFoundException;

    CompanyMemberDTO findById(Long id) throws NotFoundException;

    CompanyMemberDTO findByUserId(Long userId) throws NotFoundException;

    Page<CompanyMemberDTO> findAll(Pageable pageable) throws NotFoundException;

    Page<CompanyMemberDTO> findAllActiveMember(Pageable pageable) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
