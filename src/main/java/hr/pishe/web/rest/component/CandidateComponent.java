package hr.pishe.web.rest.component;

import hr.pishe.domain.enumeration.CandidateState;
import hr.pishe.service.dto.CandidateDTO;
import hr.pishe.web.rest.vm.CandidateFilterVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateComponent {

    ResponseVM<CandidateDTO> save(CandidateDTO candidateDTO) throws NotFoundException;

    ResponseVM<CandidateDTO> updateState(Long id, CandidateState state, Long pipelineId) throws NotFoundException;

    ResponseVM<CandidateDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CandidateDTO>> findByFilter(CandidateFilterVM filterVM, Pageable pageable) throws NotFoundException;

}
