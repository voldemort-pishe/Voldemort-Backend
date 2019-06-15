package hr.pishe.web.rest.component.impl;

import hr.pishe.service.CandidateEvaluationCriteriaService;
import hr.pishe.service.CandidateService;
import hr.pishe.service.EvaluationCriteriaService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.CandidateEvaluationCriteriaDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.CandidateMapper;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.CandidateEvaluationCriteriaComponent;
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
public class CandidateEvaluationCriteriaComponentImpl implements CandidateEvaluationCriteriaComponent {

    private final Logger log = LoggerFactory.getLogger(CandidateEvaluationCriteriaComponentImpl.class);

    private final CandidateEvaluationCriteriaService candidateEvaluationCriteriaService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;
    private final EvaluationCriteriaService evaluationCriteriaService;

    public CandidateEvaluationCriteriaComponentImpl(CandidateEvaluationCriteriaService candidateEvaluationCriteriaService,
                                                    UserService userService,
                                                    UserMapper userMapper,
                                                    CandidateService candidateService,
                                                    CandidateMapper candidateMapper,
                                                    EvaluationCriteriaService evaluationCriteriaService) {
        this.candidateEvaluationCriteriaService = candidateEvaluationCriteriaService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.candidateService = candidateService;
        this.candidateMapper = candidateMapper;
        this.evaluationCriteriaService = evaluationCriteriaService;
    }

    @Override
    public ResponseVM<CandidateEvaluationCriteriaDTO> save(CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO) throws NotFoundException {
        log.debug("Request to save candidate evaluation criteria via component : {}", candidateEvaluationCriteriaDTO);
        candidateEvaluationCriteriaDTO = candidateEvaluationCriteriaService.save(candidateEvaluationCriteriaDTO);
        ResponseVM<CandidateEvaluationCriteriaDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateEvaluationCriteriaDTO);
        responseVM.setInclude(this.createIncluded(candidateEvaluationCriteriaDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CandidateEvaluationCriteriaDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find candidate evaluation criteria by id : {}", id);
        Optional<CandidateEvaluationCriteriaDTO> candidateEvaluationCriteriaDTO = candidateEvaluationCriteriaService.findById(id);
        if (candidateEvaluationCriteriaDTO.isPresent()) {
            ResponseVM<CandidateEvaluationCriteriaDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateEvaluationCriteriaDTO.get());
            responseVM.setInclude(this.createIncluded(candidateEvaluationCriteriaDTO.get()));
            return responseVM;
        } else {
            throw new NotFoundException("candidate evaluation not found");
        }
    }

    @Override
    public Page<ResponseVM<CandidateEvaluationCriteriaDTO>> findAllByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException {
        log.debug("Request to find all candidate evaluation criteria by candidateId : {}", candidateId);
        Page<CandidateEvaluationCriteriaDTO> candidateEvaluationCriteriaDTOS =
            candidateEvaluationCriteriaService.findAllByCandidateId(candidateId, pageable);
        List<ResponseVM<CandidateEvaluationCriteriaDTO>> responseVMS = new ArrayList<>();
        for (CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO : candidateEvaluationCriteriaDTOS) {
            ResponseVM<CandidateEvaluationCriteriaDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateEvaluationCriteriaDTO);
            responseVM.setInclude(this.createIncluded(candidateEvaluationCriteriaDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateEvaluationCriteriaDTOS);
    }

    private Map<String, Object> createIncluded(CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        Optional<UserDTO> userDTOOptional = userService.findById(candidateEvaluationCriteriaDTO.getUserId());
        userDTOOptional.ifPresent(userDTO -> included.put("owner", userMapper.dtoToVm(userDTO)));

        included.put("candidate", candidateMapper.dtoToVm(candidateService.findById(candidateEvaluationCriteriaDTO.getCandidateId())));

        evaluationCriteriaService.findById(candidateEvaluationCriteriaDTO.getEvaluationCriteriaId()).ifPresent(evaluationCriteriaDTO -> {
            included.put("evaluationCriteria", evaluationCriteriaDTO);
        });

        return included;
    }
}
