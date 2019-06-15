package hr.pishe.web.rest.component.impl;

import hr.pishe.service.CandidateScheduleService;
import hr.pishe.service.CandidateService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.CandidateScheduleDTO;
import hr.pishe.service.mapper.CandidateMapper;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.CandidateScheduleComponent;
import hr.pishe.web.rest.util.PageMaker;
import hr.pishe.web.rest.vm.response.ResponseVM;
import hr.pishe.web.rest.vm.response.UserIncludeVM;
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
import java.util.stream.Collectors;

@Component
public class CandidateScheduleComponentImpl implements CandidateScheduleComponent {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleComponentImpl.class);

    private final CandidateScheduleService candidateScheduleService;
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public CandidateScheduleComponentImpl(CandidateScheduleService candidateScheduleService,
                                          CandidateService candidateService,
                                          CandidateMapper candidateMapper, UserService userService, UserMapper userMapper) {
        this.candidateScheduleService = candidateScheduleService;
        this.candidateService = candidateService;
        this.candidateMapper = candidateMapper;
        this.userService = userService;
        this.userMapper = userMapper;
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
        List<UserIncludeVM> memberUsers = candidateScheduleDTO
            .getMember()
            .stream()
            .map(candidateScheduleMemberDTO -> userMapper.dtoToVm(
                userService.findById(candidateScheduleMemberDTO.getUserId()).get()
            ))
            .collect(Collectors.toList());
        included.put("memberUsers", memberUsers);

        return included;
    }
}
