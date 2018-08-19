package io.avand.web.rest.component;

import io.avand.service.dto.JobDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobComponent {

    ResponseVM<JobDTO> save(JobDTO jobDTO) throws NotFoundException;

    ResponseVM<JobDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<JobDTO>> findAll(Pageable pageable) throws NotFoundException;

    Page<ResponseVM<JobDTO>> findAllByCompany(Long companyId, Pageable pageable) throws NotFoundException;
}
