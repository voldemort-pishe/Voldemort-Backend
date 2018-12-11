package io.avand.service;

import io.avand.domain.enumeration.CandidateState;
import io.avand.service.dto.CandidateDTO;
import io.avand.web.rest.vm.CandidateFilterVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CandidateService {

    CandidateDTO save(CandidateDTO candidateDTO) throws NotFoundException;

    CandidateDTO save(CandidateDTO candidateDTO, String companySubDomain) throws NotFoundException;

    CandidateDTO updateState(Long id, CandidateState state) throws NotFoundException;

    CandidateDTO updatePipeline(Long id, Long pipelineId) throws NotFoundException;

    CandidateDTO findById(Long id) throws NotFoundException;

    Page<CandidateDTO> findByFilter(CandidateFilterVM filterVM, Pageable pageable) throws NotFoundException;

    void delete(Long id);

}
