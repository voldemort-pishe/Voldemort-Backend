package hr.pishe.service;

import hr.pishe.service.dto.CompanyMemberDTO;
import hr.pishe.web.rest.vm.CompanyMemberFilterVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyMemberService {

    CompanyMemberDTO save(CompanyMemberDTO companyMemberDTO) throws NotFoundException;

    List<CompanyMemberDTO> saveAll(List<CompanyMemberDTO> memberDTOS) throws NotFoundException;

    CompanyMemberDTO findById(Long id) throws NotFoundException;

    CompanyMemberDTO findByUserId(Long userId) throws NotFoundException;

    Optional<CompanyMemberDTO> findByLogin(String login);

    Page<CompanyMemberDTO> findAllByFilter(CompanyMemberFilterVM filterVM, Pageable pageable)
        throws NotFoundException;

    Page<CompanyMemberDTO> findAllActiveMember(Pageable pageable) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
