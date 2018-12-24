package io.avand.web.rest.component;

import io.avand.service.dto.CandidateSocialDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateSocialComponent {
    ResponseVM<CandidateSocialDTO> save(CandidateSocialDTO candidateSocialDTO) throws NotFoundException;

    ResponseVM<CandidateSocialDTO> findById(Long id) throws NotFoundException;

    List<ResponseVM<CandidateSocialDTO>> findAllByCandidateId(Long candidateId) throws NotFoundException;
}
