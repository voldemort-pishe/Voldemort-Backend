package io.avand.web.rest.component.impl;

import io.avand.service.*;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CompanyPipelineDTO;
import io.avand.service.dto.FileDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.CompanyPipelineMapper;
import io.avand.service.mapper.JobMapper;
import io.avand.service.mapper.UserMapper;
import io.avand.web.rest.component.CandidateComponent;
import io.avand.web.rest.util.PageMaker;
import io.avand.web.rest.vm.response.CompanyPipelineIncludeVM;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CandidateComponentImpl implements CandidateComponent {

    private final Logger log = LoggerFactory.getLogger(CandidateComponentImpl.class);
    private final CandidateService candidateService;
    private final UserService userService;
    private final JobService jobService;
    private final FileService fileService;
    private final UserMapper userMapper;
    private final JobMapper jobMapper;
    private final CompanyPipelineService companyPipelineService;
    private final CompanyPipelineMapper companyPipelineMapper;


    public CandidateComponentImpl(CandidateService candidateService,
                                  UserService userService,
                                  JobService jobService,
                                  FileService fileService,
                                  UserMapper userMapper,
                                  JobMapper jobMapper,
                                  CompanyPipelineService companyPipelineService,
                                  CompanyPipelineMapper companyPipelineMapper) {
        this.candidateService = candidateService;
        this.userService = userService;
        this.jobService = jobService;
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.jobMapper = jobMapper;
        this.companyPipelineService = companyPipelineService;
        this.companyPipelineMapper = companyPipelineMapper;
    }

    @Override
    public ResponseVM<CandidateDTO> save(CandidateDTO candidateDTO) throws NotFoundException {
        log.debug("Request to save candidateDTO via component : {}", candidateDTO);
        candidateDTO = candidateService.save(candidateDTO);
        ResponseVM<CandidateDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateDTO);
        responseVM.setInclude(this.createIncluded(candidateDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CandidateDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find candidateDTO by id via component : {}", id);
        CandidateDTO candidateDTO = candidateService.findById(id);
        ResponseVM<CandidateDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateDTO);
        responseVM.setInclude(this.createIncluded(candidateDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<CandidateDTO>> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to findAll candidateDTO via component");
        Page<CandidateDTO> candidateDTOS = candidateService.findAll(pageable);
        List<ResponseVM<CandidateDTO>> responseVMS = new ArrayList<>();
        for (CandidateDTO candidateDTO : candidateDTOS) {
            ResponseVM<CandidateDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateDTO);
            responseVM.setInclude(this.createIncluded(candidateDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateDTOS);
    }

    @Override
    public Page<ResponseVM<CandidateDTO>> findByJobId(Long jobId, Pageable pageable) throws NotFoundException {
        log.debug("Request to findAll candidate by jobId via component : {}", jobId);
        Page<CandidateDTO> candidateDTOS = candidateService.findByJobId(jobId, pageable);
        List<ResponseVM<CandidateDTO>> responseVMS = new ArrayList<>();
        for (CandidateDTO candidateDTO : candidateDTOS) {
            ResponseVM<CandidateDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateDTO);
            responseVM.setInclude(this.createIncluded(candidateDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateDTOS);
    }

    @Override
    public Page<ResponseVM<CandidateDTO>> findByCompanyId(Long companyId, Pageable pageable) throws NotFoundException {
        log.debug("Request to findAll candidate by companyId via component : {}", companyId);
        Page<CandidateDTO> candidateDTOS = candidateService.findByCompanyId(companyId, pageable);
        List<ResponseVM<CandidateDTO>> responseVMS = new ArrayList<>();
        for (CandidateDTO candidateDTO : candidateDTOS) {
            ResponseVM<CandidateDTO> responseVM = new ResponseVM<>();
            responseVM.setData(candidateDTO);
            responseVM.setInclude(this.createIncluded(candidateDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, candidateDTOS);
    }

    private Map<String, Object> createIncluded(CandidateDTO candidateDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        if(candidateDTO.getCandidatePipeline() != null) {
            try {
                CompanyPipelineDTO companyPipelineDTO = companyPipelineService.findOne(candidateDTO.getCandidatePipeline());
                included.put("pipeline", companyPipelineMapper.dtoToVm(companyPipelineDTO));
            } catch (NotFoundException ignore) {
            }
        }

        Optional<FileDTO> fileDTOOptional = fileService.findById(candidateDTO.getFileId());
        fileDTOOptional.ifPresent(fileDTO -> included.put("file", fileDTO));

        included.put("job", jobMapper.dtoToVm(jobService.findById(candidateDTO.getJobId())));

        return included;
    }
}
