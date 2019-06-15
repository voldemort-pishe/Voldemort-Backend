package hr.pishe.web.rest.component.impl;

import hr.pishe.service.CandidateService;
import hr.pishe.service.FeedbackService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.FeedbackDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.CandidateMapper;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.FeedbackComponent;
import hr.pishe.web.rest.util.PageMaker;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedbackComponentImpl implements FeedbackComponent {

    private final Logger log = LoggerFactory.getLogger(FeedbackComponentImpl.class);
    private final FeedbackService feedbackService;
    private final CandidateService candidateService;
    private final UserService userService;
    private final CandidateMapper candidateMapper;
    private final UserMapper userMapper;

    public FeedbackComponentImpl(FeedbackService feedbackService,
                                 CandidateService candidateService,
                                 UserService userService,
                                 CandidateMapper candidateMapper,
                                 UserMapper userMapper) {
        this.feedbackService = feedbackService;
        this.candidateService = candidateService;
        this.userService = userService;
        this.candidateMapper = candidateMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseVM<FeedbackDTO> save(FeedbackDTO feedbackDTO) throws NotFoundException {
        log.debug("Request to save feedbackDTO via component : {}", feedbackDTO);
        feedbackDTO = feedbackService.save(feedbackDTO);
        ResponseVM<FeedbackDTO> responseVM = new ResponseVM<>();
        responseVM.setData(feedbackDTO);
        responseVM.setInclude(this.createIncluded(feedbackDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<FeedbackDTO> update(FeedbackDTO feedbackDTO) throws NotFoundException {
        log.debug("Request to update feedbackDTO via component : {}", feedbackDTO);
        feedbackDTO = feedbackService.update(feedbackDTO);
        ResponseVM<FeedbackDTO> responseVM = new ResponseVM<>();
        responseVM.setData(feedbackDTO);
        responseVM.setInclude(this.createIncluded(feedbackDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<FeedbackDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find feedbackDTO by id via component : {}", id);
        FeedbackDTO feedbackDTO = feedbackService.findById(id);
        ResponseVM<FeedbackDTO> responseVM = new ResponseVM<>();
        responseVM.setData(feedbackDTO);
        responseVM.setInclude(this.createIncluded(feedbackDTO));
        return responseVM;
    }


    @Override
    public Page<ResponseVM<FeedbackDTO>> findAllByCandidate(Long candidateId, Pageable pageable) throws NotFoundException {
        log.debug("Request to findAll feedbackDTO by candidateId via component : {}", candidateId);
        Page<FeedbackDTO> feedbackDTOS = feedbackService.findAllByCandidateId(pageable, candidateId);
        List<ResponseVM<FeedbackDTO>> responseVMS = new ArrayList<>();
        for (FeedbackDTO feedbackDTO : feedbackDTOS) {
            ResponseVM<FeedbackDTO> responseVM = new ResponseVM<>();
            responseVM.setData(feedbackDTO);
            responseVM.setInclude(this.createIncluded(feedbackDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, feedbackDTOS);
    }

    private Map<String, Object> createIncluded(FeedbackDTO feedbackDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();
        if (feedbackDTO.getUserId() != null) {
            Optional<UserDTO> userDTOOptional = userService.findById(feedbackDTO.getUserId());
            userDTOOptional.ifPresent(userDTO -> included.put("owner", userMapper.dtoToVm(userDTO)));
        }
        included.put("candidate", candidateMapper.dtoToVm(candidateService.findById(feedbackDTO.getCandidateId())));
        return included;
    }
}
