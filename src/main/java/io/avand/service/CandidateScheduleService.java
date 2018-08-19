package io.avand.service;

import io.avand.service.dto.CandidateScheduleDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

public interface CandidateScheduleService {

    CandidateScheduleDTO save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException;

    CandidateScheduleDTO findById(Long id) throws NotFoundException;

    Page<CandidateScheduleDTO> findByOwnerId(Pageable pageable) throws NotFoundException;

    Page<CandidateScheduleDTO> findByOwnerIdAndDateBetween(ZonedDateTime startDate, ZonedDateTime endDate,Pageable pageable) throws NotFoundException;

    Page<CandidateScheduleDTO> findByCandidateId(Long candidateId,Pageable pageable);


    void delete(Long id) throws NotFoundException;

}
