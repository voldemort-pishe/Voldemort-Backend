package io.avand.web.rest.component;

import io.avand.service.dto.CompanyPipelineDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyPipelineComponent {

    ResponseVM<CompanyPipelineDTO> save(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException;

    ResponseVM<CompanyPipelineDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CompanyPipelineDTO>> findAll(Pageable pageable) throws NotFoundException;
}
