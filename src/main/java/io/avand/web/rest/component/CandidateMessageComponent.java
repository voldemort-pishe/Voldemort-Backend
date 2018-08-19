package io.avand.web.rest.component;

import io.avand.service.dto.CandidateMessageDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CandidateMessageComponent {

    ResponseVM<CandidateMessageDTO> save(CandidateMessageDTO candidateMessageDTO) throws NotFoundException;

    Page<ResponseVM<CandidateMessageDTO>> findByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException;

    ResponseVM<CandidateMessageDTO> findById(Long id) throws NotFoundException;
}
