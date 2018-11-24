package io.avand.service;

import io.avand.service.dto.CandidateScheduleMemberDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateScheduleMemberService {
    CandidateScheduleMemberDTO save(CandidateScheduleMemberDTO candidateScheduleMemberDTO) throws NotFoundException;

    CandidateScheduleMemberDTO findById(Long id) throws NotFoundException;

    List<CandidateScheduleMemberDTO> findByScheduleId(Long id);

    void delete(Long id) throws NotFoundException;
}
