package io.avand.service;

import io.avand.service.dto.CandidateDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CandidateService {

    CandidateDTO save(CandidateDTO candidateDTO) throws NotFoundException;

    CandidateDTO save(CandidateDTO candidateDTO,String companySubDomain) throws NotFoundException;

    CandidateDTO findById(Long id) throws NotFoundException;

    Page<CandidateDTO> findAll(Pageable pageable);

    Page<CandidateDTO> findByJobId(Long jobId,Pageable pageable) throws NotFoundException;

    Page<CandidateDTO> findByCompanyId(Long jobId,Pageable pageable) throws NotFoundException;

    void delete(Long id);

}
