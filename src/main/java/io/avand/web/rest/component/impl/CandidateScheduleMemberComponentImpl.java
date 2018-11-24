package io.avand.web.rest.component.impl;

import io.avand.service.CandidateScheduleMemberService;
import io.avand.service.UserService;
import io.avand.service.dto.CandidateScheduleMemberDTO;
import io.avand.service.mapper.UserMapper;
import io.avand.web.rest.component.CandidateScheduleMemberComponent;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CandidateScheduleMemberComponentImpl implements CandidateScheduleMemberComponent {

    private final Logger log = LoggerFactory.getLogger(CandidateScheduleMemberComponentImpl.class);

    private final CandidateScheduleMemberService candidateScheduleMemberService;
    private final UserService userService;
    private final UserMapper userMapper;

    public CandidateScheduleMemberComponentImpl(CandidateScheduleMemberService candidateScheduleMemberService,
                                                UserService userService,
                                                UserMapper userMapper) {
        this.candidateScheduleMemberService = candidateScheduleMemberService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseVM<CandidateScheduleMemberDTO> save(CandidateScheduleMemberDTO candidateScheduleMemberDTO)
        throws NotFoundException {
        log.debug("Request to save candidateScheduleMember via component : {}", candidateScheduleMemberDTO);
        candidateScheduleMemberDTO = candidateScheduleMemberService.save(candidateScheduleMemberDTO);
        ResponseVM<CandidateScheduleMemberDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateScheduleMemberDTO);
        responseVM.setInclude(this.createIncluded(candidateScheduleMemberDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CandidateScheduleMemberDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find candidateScheduleMember by id via component : {}", id);
        CandidateScheduleMemberDTO candidateScheduleMemberDTO = candidateScheduleMemberService.findById(id);
        ResponseVM<CandidateScheduleMemberDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateScheduleMemberDTO);
        responseVM.setInclude(this.createIncluded(candidateScheduleMemberDTO));
        return responseVM;
    }

    @Override
    public List<ResponseVM<CandidateScheduleMemberDTO>> findByScheduleId(Long id) throws NotFoundException {
        log.debug("Request to find candidateScheduleMember by scheduleId via component :{}", id);
        List<CandidateScheduleMemberDTO> candidateScheduleMemberDTOS =
            candidateScheduleMemberService.findByScheduleId(id);
        List<ResponseVM<CandidateScheduleMemberDTO>> responseVMS = new ArrayList<>();
        for (CandidateScheduleMemberDTO candidateScheduleMemberDTO : candidateScheduleMemberDTOS) {
            ResponseVM<CandidateScheduleMemberDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateScheduleMemberDTO);
            responseVM.setInclude(this.createIncluded(candidateScheduleMemberDTO));
            responseVMS.add(responseVM);
        }
        return responseVMS;
    }

    private Map<String, Object> createIncluded(CandidateScheduleMemberDTO candidateScheduleMemberDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();
        included.put("owner", userMapper.dtoToVm(userService.findById(candidateScheduleMemberDTO.getUserId()).get()));
        return included;
    }
}
