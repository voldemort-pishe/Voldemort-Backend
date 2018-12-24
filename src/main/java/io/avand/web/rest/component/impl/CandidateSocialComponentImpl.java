package io.avand.web.rest.component.impl;

import io.avand.service.CandidateService;
import io.avand.service.CandidateSocialService;
import io.avand.service.dto.CandidateSocialDTO;
import io.avand.service.mapper.CandidateMapper;
import io.avand.service.mapper.CandidateSocialMapper;
import io.avand.web.rest.component.CandidateSocialComponent;
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
public class CandidateSocialComponentImpl implements CandidateSocialComponent {

    private final Logger log = LoggerFactory.getLogger(CandidateSocialComponentImpl.class);
    private final CandidateSocialService candidateSocialService;
    private final CandidateService candidateService;
    private final CandidateSocialMapper candidateSocialMapper;
    private final CandidateMapper candidateMapper;

    public CandidateSocialComponentImpl(CandidateSocialService candidateSocialService,
                                        CandidateService candidateService,
                                        CandidateSocialMapper candidateSocialMapper,
                                        CandidateMapper candidateMapper) {
        this.candidateSocialService = candidateSocialService;
        this.candidateService = candidateService;
        this.candidateSocialMapper = candidateSocialMapper;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public ResponseVM<CandidateSocialDTO> save(CandidateSocialDTO candidateSocialDTO) throws NotFoundException {
        candidateSocialDTO = candidateSocialService.save(candidateSocialDTO);
        ResponseVM<CandidateSocialDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateSocialDTO);
        responseVM.setInclude(this.createIncluded(candidateSocialDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CandidateSocialDTO> findById(Long id) throws NotFoundException {
        CandidateSocialDTO candidateSocialDTO = candidateSocialService.findById(id);
        ResponseVM<CandidateSocialDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateSocialDTO);
        responseVM.setInclude(this.createIncluded(candidateSocialDTO));
        return responseVM;
    }

    @Override
    public List<ResponseVM<CandidateSocialDTO>> findAllByCandidateId(Long candidateId) throws NotFoundException {
        List<CandidateSocialDTO> candidateSocialDTOS = candidateSocialService.findAllByCandidateId(candidateId);
        List<ResponseVM<CandidateSocialDTO>> responseVMS = new ArrayList<>();
        for (CandidateSocialDTO candidateSocialDTO : candidateSocialDTOS) {
            ResponseVM<CandidateSocialDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateSocialDTO);
            responseVM.setInclude(this.createIncluded(candidateSocialDTO));
            responseVMS.add(responseVM);
        }
        return responseVMS;
    }

    private Map<String, Object> createIncluded(CandidateSocialDTO candidateSocialDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        included.put("candidate", candidateMapper.dtoToVm(candidateService.findById(candidateSocialDTO.getCandidateId())));

        return included;
    }
}
