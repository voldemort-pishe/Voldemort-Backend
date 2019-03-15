package hr.pishe.web.rest.component;

import hr.pishe.domain.enumeration.CandidateScheduleMemberStatus;
import hr.pishe.service.dto.CandidateScheduleMemberDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateScheduleMemberComponent {
    ResponseVM<CandidateScheduleMemberDTO> save(CandidateScheduleMemberDTO candidateScheduleMemberDTO) throws NotFoundException;

    ResponseVM<CandidateScheduleMemberDTO> changeStatue(Long scheduleId, CandidateScheduleMemberStatus status) throws NotFoundException;

    ResponseVM<CandidateScheduleMemberDTO> findById(Long id) throws NotFoundException;

    List<ResponseVM<CandidateScheduleMemberDTO>> findByScheduleId(Long id) throws NotFoundException;
}
