package io.avand.service;

import io.avand.service.dto.CompanyMemberDTO;
import io.avand.web.rest.vm.CompanyMemberFilterVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyMemberService {

    CompanyMemberDTO save(CompanyMemberDTO companyMemberDTO) throws NotFoundException;

    List<CompanyMemberDTO> saveAll(List<String> emails) throws NotFoundException;

    CompanyMemberDTO findById(Long id) throws NotFoundException;

    CompanyMemberDTO findByUserId(Long userId) throws NotFoundException;

    Page<CompanyMemberDTO> findAllByFilter(CompanyMemberFilterVM filterVM, Pageable pageable)
        throws NotFoundException;

    Page<CompanyMemberDTO> findAllActiveMember(Pageable pageable) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
