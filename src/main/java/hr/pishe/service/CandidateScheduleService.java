package hr.pishe.service;

import hr.pishe.service.dto.CandidateScheduleDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;

public interface CandidateScheduleService {

    CandidateScheduleDTO save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException, IOException, URISyntaxException;

    CandidateScheduleDTO update(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException;

    CandidateScheduleDTO findById(Long id) throws NotFoundException;

    Page<CandidateScheduleDTO> findAll(Pageable pageable) throws NotFoundException;

    Page<CandidateScheduleDTO> findByDate(ZonedDateTime startDate, ZonedDateTime endDate, Pageable pageable) throws NotFoundException;

    Page<CandidateScheduleDTO> findByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

}
