package io.avand.web.rest.component;

import io.avand.service.dto.CompanyDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

public interface CompanyComponent {

    ResponseVM<CompanyDTO> save(CompanyDTO companyDTO) throws NotFoundException;

    ResponseVM<CompanyDTO> findById(Long id) throws NotFoundException;

}
