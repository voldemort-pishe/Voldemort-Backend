package io.avand.web.rest.component;

import io.avand.service.dto.CompanyMemberDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CompanyMemberComponent {

    List<ResponseVM<CompanyMemberDTO>> save(List<String> emails, Long companyId) throws NotFoundException;

    ResponseVM<CompanyMemberDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CompanyMemberDTO>> findAll(Long companyId, Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CompanyMemberDTO>> findAllActiveMember(Long companyId, Pageable pageable) throws NotFoundException;

}
