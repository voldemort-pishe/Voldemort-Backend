package hr.pishe.service;

import hr.pishe.domain.enumeration.JobStatus;
import hr.pishe.service.dto.JobDTO;
import hr.pishe.web.rest.vm.JobFilterVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    JobDTO save(JobDTO jobDTO) throws NotFoundException;

    JobDTO update(JobDTO jobDTO) throws NotFoundException;

    void publish(Long jobId, JobStatus status) throws NotFoundException;

    JobDTO findById(Long id) throws NotFoundException;

    List<JobDTO> findAllByCompanySubDomain(String subDomain);

    JobDTO findByJobUniqueIdAndCompanySubDomain(String uniqueId, String subDomain) throws NotFoundException;

    Page<JobDTO> findAllByFilter(Pageable pageable, JobFilterVM filterVM) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
