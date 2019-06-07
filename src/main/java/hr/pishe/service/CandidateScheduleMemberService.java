package hr.pishe.service;

import hr.pishe.domain.enumeration.CandidateScheduleMemberStatus;
import hr.pishe.service.dto.CandidateScheduleMemberDTO;
import javassist.NotFoundException;

import java.util.List;
import java.util.Set;

public interface CandidateScheduleMemberService {
    CandidateScheduleMemberDTO save(CandidateScheduleMemberDTO candidateScheduleMemberDTO) throws NotFoundException;

    Set<CandidateScheduleMemberDTO> saveAll(List<CandidateScheduleMemberDTO> candidateScheduleMemberDTOS) throws NotFoundException;

    CandidateScheduleMemberDTO changeStatus(Long scheduleId, CandidateScheduleMemberStatus status) throws NotFoundException;

    CandidateScheduleMemberDTO findById(Long id) throws NotFoundException;

    List<CandidateScheduleMemberDTO> findByScheduleId(Long id);

    List<CandidateScheduleMemberDTO> findByUserId(Long userId) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
