package hr.pishe.web.rest.component;

import hr.pishe.service.dto.CandidateEvaluationCriteriaDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateEvaluationCriteriaComponent {
    ResponseVM<CandidateEvaluationCriteriaDTO> save(CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO) throws NotFoundException;

    ResponseVM<CandidateEvaluationCriteriaDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CandidateEvaluationCriteriaDTO>> findAllByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException;
}
