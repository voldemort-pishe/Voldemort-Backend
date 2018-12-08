package io.avand.web.rest.component.impl;

import io.avand.service.CandidateScheduleService;
import io.avand.service.CandidateService;
import io.avand.service.dto.CandidateScheduleDTO;
import io.avand.service.mapper.CandidateMapper;
import io.avand.web.rest.component.CandidateScheduleComponent;
import io.avand.web.rest.util.PageMaker;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class CandidateScheduleComponentImpl implements CandidateScheduleComponent {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleComponentImpl.class);

    private final CandidateScheduleService candidateScheduleService;
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;

    public CandidateScheduleComponentImpl(CandidateScheduleService candidateScheduleService,
                                          CandidateService candidateService,
                                          CandidateMapper candidateMapper) {
        this.candidateScheduleService = candidateScheduleService;
        this.candidateService = candidateService;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public ResponseVM<CandidateScheduleDTO> save(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException, IOException, URISyntaxException {
        log.debug("Request to save candidateScheduleDTO via component : {}", candidateScheduleDTO);
        candidateScheduleDTO = candidateScheduleService.save(candidateScheduleDTO);
        ResponseVM<CandidateScheduleDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateScheduleDTO);
        responseVM.setInclude(this.createIncluded(candidateScheduleDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CandidateScheduleDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find candidateScheduleDTO by id via component : {}", id);
        CandidateScheduleDTO candidateScheduleDTO = candidateScheduleService.findById(id);
        ResponseVM<CandidateScheduleDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateScheduleDTO);
        responseVM.setInclude(this.createIncluded(candidateScheduleDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<CandidateScheduleDTO>> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to find candidateScheduleDTO by owner via component");
        Page<CandidateScheduleDTO> candidateScheduleDTOS = candidateScheduleService.findAll(pageable);
        List<ResponseVM<CandidateScheduleDTO>> responseVMS = new ArrayList<>();
        for (CandidateScheduleDTO candidateScheduleDTO : candidateScheduleDTOS) {
            ResponseVM<CandidateScheduleDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateScheduleDTO);
            responseVM.setInclude(this.createIncluded(candidateScheduleDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateScheduleDTOS);
    }

    @Override
    public Page<ResponseVM<CandidateScheduleDTO>> findByDate(ZonedDateTime startDate, ZonedDateTime endDate, Pageable pageable) throws NotFoundException {
        log.debug("Request to find candidateScheduleDTO by owner and date via component");
        Page<CandidateScheduleDTO> candidateScheduleDTOS = candidateScheduleService.findByDate(startDate, endDate, pageable);
        List<ResponseVM<CandidateScheduleDTO>> responseVMS = new ArrayList<>();
        for (CandidateScheduleDTO candidateScheduleDTO : candidateScheduleDTOS) {
            ResponseVM<CandidateScheduleDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateScheduleDTO);
            responseVM.setInclude(this.createIncluded(candidateScheduleDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateScheduleDTOS);
    }

    @Override
    public Page<ResponseVM<CandidateScheduleDTO>> findByCandidate(Long candidateId, Pageable pageable) throws NotFoundException {
        log.debug("Request to find candidateScheduleDTO by candidate via component");
        Page<CandidateScheduleDTO> candidateScheduleDTOS = candidateScheduleService.findByCandidateId(candidateId, pageable);
        List<ResponseVM<CandidateScheduleDTO>> responseVMS = new ArrayList<>();
        for (CandidateScheduleDTO candidateScheduleDTO : candidateScheduleDTOS) {
            ResponseVM<CandidateScheduleDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateScheduleDTO);
            responseVM.setInclude(this.createIncluded(candidateScheduleDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateScheduleDTOS);
    }

    private Map<String, Object> createIncluded(CandidateScheduleDTO candidateScheduleDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        included.put("candidate", candidateMapper.dtoToVm(candidateService.findById(candidateScheduleDTO.getCandidateId())));

        return included;
    }
}
