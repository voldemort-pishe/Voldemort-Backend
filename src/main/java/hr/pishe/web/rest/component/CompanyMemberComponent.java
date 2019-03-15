package hr.pishe.web.rest.component;

import hr.pishe.service.dto.CompanyMemberDTO;
import hr.pishe.web.rest.vm.CompanyMemberFilterVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyMemberComponent {

    ResponseVM<CompanyMemberDTO> save(CompanyMemberDTO companyMemberDTO) throws NotFoundException;

    List<ResponseVM<CompanyMemberDTO>> saveAll(List<CompanyMemberDTO> memberDTOS) throws NotFoundException;

    ResponseVM<CompanyMemberDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CompanyMemberDTO>> findAllByFilter(CompanyMemberFilterVM filterVM, Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CompanyMemberDTO>> findAllActiveMember(Pageable pageable) throws NotFoundException;

}
