package hr.pishe.web.rest.component;

import hr.pishe.service.dto.CompanyDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

public interface CompanyComponent {

    ResponseVM<CompanyDTO> save(CompanyDTO companyDTO) throws NotFoundException;

    ResponseVM<CompanyDTO> findById(Long id) throws NotFoundException;

}
