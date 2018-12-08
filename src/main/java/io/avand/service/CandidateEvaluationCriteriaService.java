package io.avand.service;

import io.avand.service.dto.CandidateEvaluationCriteriaDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CandidateEvaluationCriteriaService {

    CandidateEvaluationCriteriaDTO save(CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO) throws NotFoundException;

    Optional<CandidateEvaluationCriteriaDTO> findById(Long id) throws NotFoundException;

    Page<CandidateEvaluationCriteriaDTO> findAllByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
