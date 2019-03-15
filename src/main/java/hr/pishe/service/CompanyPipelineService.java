package hr.pishe.service;

import hr.pishe.service.dto.CompanyPipelineDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyPipelineService {

    CompanyPipelineDTO save(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException;

    Page<CompanyPipelineDTO> findAll(Pageable pageable) throws NotFoundException;

    CompanyPipelineDTO findOne(Long id) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
