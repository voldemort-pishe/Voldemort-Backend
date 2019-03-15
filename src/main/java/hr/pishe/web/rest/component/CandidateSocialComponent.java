package hr.pishe.web.rest.component;

import hr.pishe.service.dto.CandidateSocialDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateSocialComponent {
    ResponseVM<CandidateSocialDTO> save(CandidateSocialDTO candidateSocialDTO) throws NotFoundException;

    ResponseVM<CandidateSocialDTO> findById(Long id) throws NotFoundException;

    List<ResponseVM<CandidateSocialDTO>> findAllByCandidateId(Long candidateId) throws NotFoundException;
}
