package hr.pishe.web.rest.component;

import hr.pishe.service.dto.CandidateScheduleDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;

public interface CandidateScheduleComponent {

    ResponseVM<CandidateScheduleDTO> save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException, IOException, URISyntaxException;

    ResponseVM<CandidateScheduleDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CandidateScheduleDTO>> findAll(Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CandidateScheduleDTO>> findByDate(ZonedDateTime startDate, ZonedDateTime endDate, Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CandidateScheduleDTO>> findByCandidate(Long candidateId, Pageable pageable) throws NotFoundException;
}
