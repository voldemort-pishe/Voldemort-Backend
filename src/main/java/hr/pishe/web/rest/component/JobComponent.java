package hr.pishe.web.rest.component;

import hr.pishe.service.dto.JobDTO;
import hr.pishe.web.rest.vm.JobFilterVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobComponent {

    ResponseVM<JobDTO> save(JobDTO jobDTO) throws NotFoundException;

    ResponseVM<JobDTO> update(JobDTO jobDTO) throws NotFoundException;

    ResponseVM<JobDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<JobDTO>> findAllByFilter(JobFilterVM filterVM, Pageable pageable) throws NotFoundException;
}
