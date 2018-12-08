package io.avand.service;

import io.avand.service.dto.CandidateScheduleMemberDTO;
import javassist.NotFoundException;

import java.util.List;
import java.util.Set;

public interface CandidateScheduleMemberService {
    CandidateScheduleMemberDTO save(CandidateScheduleMemberDTO candidateScheduleMemberDTO) throws NotFoundException;

    Set<CandidateScheduleMemberDTO> saveAll(Set<CandidateScheduleMemberDTO> candidateScheduleMemberDTOS) throws NotFoundException;

    CandidateScheduleMemberDTO findById(Long id) throws NotFoundException;

    List<CandidateScheduleMemberDTO> findByScheduleId(Long id);

    void delete(Long id) throws NotFoundException;
}
