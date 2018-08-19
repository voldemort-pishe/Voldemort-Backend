package io.avand.web.rest.component.impl;

import io.avand.service.CandidateService;
import io.avand.service.FeedbackService;
import io.avand.service.dto.FeedbackDTO;
import io.avand.service.mapper.CandidateMapper;
import io.avand.web.rest.component.FeedbackComponent;
import io.avand.web.rest.util.PageMaker;
import io.avand.web.rest.vm.response.ResponseVM;
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
public class FeedbackComponentImpl implements FeedbackComponent {

    private final Logger log = LoggerFactory.getLogger(FeedbackComponentImpl.class);
    private final FeedbackService feedbackService;
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;

    public FeedbackComponentImpl(FeedbackService feedbackService,
                                 CandidateService candidateService,
                                 CandidateMapper candidateMapper) {
        this.feedbackService = feedbackService;
        this.candidateService = candidateService;
        this.candidateMapper = candidateMapper;
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
    public Page<ResponseVM<FeedbackDTO>> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to findAll feedbackDTO via component");
        Page<FeedbackDTO> feedbackDTOS = feedbackService.findAll(pageable);
        List<ResponseVM<FeedbackDTO>> responseVMS = new ArrayList<>();
        for (FeedbackDTO feedbackDTO : feedbackDTOS) {
            ResponseVM<FeedbackDTO> responseVM = new ResponseVM<>();
            responseVM.setData(feedbackDTO);
            responseVM.setInclude(this.createIncluded(feedbackDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, feedbackDTOS);
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
        included.put("candidate", candidateMapper.dtoToVm(candidateService.findById(feedbackDTO.getCandidateId())));
        return included;
    }
}
