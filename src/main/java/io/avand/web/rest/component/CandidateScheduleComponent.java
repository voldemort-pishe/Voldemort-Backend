package io.avand.web.rest.component;

import io.avand.service.CandidateService;
import io.avand.service.UserService;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CandidateMessageDTO;
import io.avand.service.dto.CandidateScheduleDTO;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.vm.CandidateScheduleVm;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CandidateScheduleComponent {

    ResponseVM<CandidateScheduleDTO> save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException;

    ResponseVM<CandidateScheduleDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CandidateScheduleDTO>> findAll(Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CandidateScheduleDTO>> findByDate(ZonedDateTime startDate, ZonedDateTime endDate, Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CandidateScheduleDTO>> findByCandidate(Long candidateId, Pageable pageable) throws NotFoundException;
}
