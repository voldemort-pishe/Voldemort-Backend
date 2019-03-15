package hr.pishe.service;

import hr.pishe.service.dto.EvaluationCriteriaDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EvaluationCriteriaService {

    EvaluationCriteriaDTO save(EvaluationCriteriaDTO evaluationCriteriaDTO) throws NotFoundException;

    Optional<EvaluationCriteriaDTO> findById(Long id) throws NotFoundException;

    Page<EvaluationCriteriaDTO> findAll(Pageable pageable) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
