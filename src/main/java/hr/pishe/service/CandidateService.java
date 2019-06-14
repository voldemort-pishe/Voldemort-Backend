package hr.pishe.service;

import hr.pishe.domain.enumeration.CandidateState;
import hr.pishe.service.dto.CandidateDTO;
import hr.pishe.web.rest.vm.CandidateFilterVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateService {

    CandidateDTO save(CandidateDTO candidateDTO) throws NotFoundException;

    CandidateDTO save(CandidateDTO candidateDTO, String companySubDomain, Long companyId) throws NotFoundException;

    CandidateDTO updateState(Long id, CandidateState state, Long pipelineId) throws NotFoundException;

    CandidateDTO findById(Long id) throws NotFoundException;

    Page<CandidateDTO> findByFilter(CandidateFilterVM filterVM, Pageable pageable) throws NotFoundException;

    void delete(Long id);

}
