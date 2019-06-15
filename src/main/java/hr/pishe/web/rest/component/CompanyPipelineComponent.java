package hr.pishe.web.rest.component;

import hr.pishe.service.dto.CompanyPipelineDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyPipelineComponent {

    ResponseVM<CompanyPipelineDTO> save(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException;

    ResponseVM<CompanyPipelineDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CompanyPipelineDTO>> findAll(Pageable pageable) throws NotFoundException;
}
