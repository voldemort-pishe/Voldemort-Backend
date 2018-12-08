package io.avand.web.rest.component;

import io.avand.service.dto.CandidateScheduleMemberDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateScheduleMemberComponent {
    ResponseVM<CandidateScheduleMemberDTO> save(CandidateScheduleMemberDTO candidateScheduleMemberDTO) throws NotFoundException;

    ResponseVM<CandidateScheduleMemberDTO> findById(Long id) throws NotFoundException;

    List<ResponseVM<CandidateScheduleMemberDTO>> findByScheduleId(Long id) throws NotFoundException;
}
