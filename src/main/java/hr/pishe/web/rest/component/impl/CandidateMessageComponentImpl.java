package hr.pishe.web.rest.component.impl;

import hr.pishe.domain.enumeration.CandidateMessageOwnerType;
import hr.pishe.service.CandidateMessageService;
import hr.pishe.service.CandidateService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.CandidateMessageDTO;
import hr.pishe.service.mapper.CandidateMapper;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.CandidateMessageComponent;
import hr.pishe.web.rest.util.PageMaker;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CandidateMessageComponentImpl implements CandidateMessageComponent {

    private final Logger log = LoggerFactory.getLogger(CandidateMessageComponentImpl.class);
    private final UserService userService;
    private final CandidateService candidateService;
    private final CandidateMessageService candidateMessageService;
    private final UserMapper userMapper;
    private final CandidateMapper candidateMapper;

    public CandidateMessageComponentImpl(UserService userService,
                                         CandidateService candidateService,
                                         CandidateMessageService candidateMessageService,
                                         UserMapper userMapper,
                                         CandidateMapper candidateMapper) {
        this.userService = userService;
        this.candidateService = candidateService;
        this.candidateMessageService = candidateMessageService;
        this.userMapper = userMapper;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public ResponseVM<CandidateMessageDTO> save(CandidateMessageDTO candidateMessageDTO) throws NotFoundException {
        log.debug("Request to save candidateMessageDTO via component : {}", candidateMessageDTO);
        candidateMessageDTO = candidateMessageService.save(candidateMessageDTO);
        ResponseVM<CandidateMessageDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateMessageDTO);
        responseVM.setInclude(this.createIncluded(candidateMessageDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CandidateMessageDTO> save(String subject, String message, Long parentId, Long candidateId)
        throws NotFoundException {
        log.debug("Request to save candidateMessageDTO via component : {}, {}, {}, {}", subject, message, parentId, candidateId);
        CandidateMessageDTO candidateMessageDTO = candidateMessageService.save(subject, message, parentId, candidateId);
        ResponseVM<CandidateMessageDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateMessageDTO);
        responseVM.setInclude(this.createIncluded(candidateMessageDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<CandidateMessageDTO>> findByCandidateId(Long candidateId, Pageable pageable)
        throws NotFoundException {
        log.debug("Request to find candidateMessageDTO by candidateId via component : {}", candidateId);
        Page<CandidateMessageDTO> candidateMessageDTOS =
            candidateMessageService.findByCandidateId(candidateId, pageable);
        List<ResponseVM<CandidateMessageDTO>> responseVMS = new ArrayList<>();
        for (CandidateMessageDTO candidateMessageDTO : candidateMessageDTOS) {
            ResponseVM<CandidateMessageDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateMessageDTO);
            responseVM.setInclude(this.createIncluded(candidateMessageDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateMessageDTOS);
    }

    @Override
    public ResponseVM<CandidateMessageDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find candidateMessageDTO by id via component : {}", id);
        CandidateMessageDTO candidateMessageDTO = candidateMessageService.findById(id);
        ResponseVM<CandidateMessageDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateMessageDTO);
        responseVM.setInclude(this.createIncluded(candidateMessageDTO));
        return responseVM;
    }

    private Map<String, Object> createIncluded(CandidateMessageDTO candidateMessageDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();
        if (candidateMessageDTO.getOwner() == CandidateMessageOwnerType.USER) {
            included.put("from", userMapper.dtoToVm(userService.findById(candidateMessageDTO.getFromUserId()).get()));
            included.put("to", candidateMapper.dtoToVm(candidateService.findById(candidateMessageDTO.getToUserId())));
        }else {
            included.put("from", candidateMapper.dtoToVm(candidateService.findById(candidateMessageDTO.getFromUserId())));
            included.put("to", userMapper.dtoToVm(userService.findById(candidateMessageDTO.getToUserId()).get()));
        }
        included.put("candidate", candidateMapper.dtoToVm(candidateService.findById(candidateMessageDTO.getCandidateId())));
        return included;
    }
}
