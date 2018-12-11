package io.avand.web.rest.component;

import io.avand.domain.enumeration.CandidateState;
import io.avand.service.dto.CandidateDTO;
import io.avand.web.rest.vm.CandidateFilterVM;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateComponent {

    ResponseVM<CandidateDTO> save(CandidateDTO candidateDTO) throws NotFoundException;

    ResponseVM<CandidateDTO> updateState(Long id, CandidateState state) throws NotFoundException;

    ResponseVM<CandidateDTO> updatePipeline(Long id, Long pipelineId) throws NotFoundException;

    ResponseVM<CandidateDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CandidateDTO>> findByFilter(CandidateFilterVM filterVM, Pageable pageable) throws NotFoundException;

}
