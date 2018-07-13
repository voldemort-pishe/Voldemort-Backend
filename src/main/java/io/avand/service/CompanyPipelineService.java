package io.avand.service;

import io.avand.service.dto.CompanyPipelineDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CompanyPipelineService {

    CompanyPipelineDTO save(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException;

    CompanyPipelineDTO update(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException;

    List<CompanyPipelineDTO> getAllByCompanyId(Long companyId) throws NotFoundException;

    CompanyPipelineDTO findOne(Long id) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
