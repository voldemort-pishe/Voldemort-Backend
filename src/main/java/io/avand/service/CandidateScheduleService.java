package io.avand.service;

import io.avand.service.dto.CandidateScheduleDTO;
import javassist.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

public interface CandidateScheduleService {

    CandidateScheduleDTO save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException;

    CandidateScheduleDTO findById(Long id) throws NotFoundException;

    List<CandidateScheduleDTO> findByOwnerId() throws NotFoundException;

    List<CandidateScheduleDTO> findByOwnerIdAndDateBetween(ZonedDateTime startDate, ZonedDateTime endDate) throws NotFoundException;

    List<CandidateScheduleDTO> findByCandidateId(Long candidateId);


    void delete(Long id) throws NotFoundException;

}
