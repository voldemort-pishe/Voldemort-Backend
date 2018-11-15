package io.avand.service;

import io.avand.service.dto.JobDTO;
import io.avand.web.rest.vm.JobFilterVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    JobDTO save(JobDTO jobDTO) throws NotFoundException;

    JobDTO findById(Long id) throws NotFoundException;

    List<JobDTO> findAllByCompanySubDomain(String subDomain);

    JobDTO findByJobUniqueIdAndCompanySubDomain(String uniqueId,String subDomain) throws NotFoundException;

    Page<JobDTO> findAll(Pageable pageable) throws NotFoundException;

    Page<JobDTO> findAllByCompanyId(Pageable pageable, JobFilterVM filterVM) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
