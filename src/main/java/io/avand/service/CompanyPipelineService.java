package io.avand.service;

import io.avand.service.dto.CompanyPipelineDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyPipelineService {

    CompanyPipelineDTO save(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException;

    Page<CompanyPipelineDTO> getAllByCompanyId(Long companyId, Pageable pageable) throws NotFoundException;

    CompanyPipelineDTO findOne(Long id) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
