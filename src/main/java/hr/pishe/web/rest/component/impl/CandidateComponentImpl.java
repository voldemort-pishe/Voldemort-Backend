package hr.pishe.web.rest.component.impl;

import hr.pishe.domain.enumeration.CandidateState;
import hr.pishe.service.CandidateService;
import hr.pishe.service.CompanyPipelineService;
import hr.pishe.service.FileService;
import hr.pishe.service.JobService;
import hr.pishe.service.dto.CandidateDTO;
import hr.pishe.service.dto.CompanyPipelineDTO;
import hr.pishe.service.dto.FileDTO;
import hr.pishe.service.mapper.CompanyPipelineMapper;
import hr.pishe.service.mapper.JobMapper;
import hr.pishe.web.rest.component.CandidateComponent;
import hr.pishe.web.rest.util.PageMaker;
import hr.pishe.web.rest.vm.CandidateFilterVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
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
    private final JobService jobService;
    private final FileService fileService;
    private final JobMapper jobMapper;
    private final CompanyPipelineService companyPipelineService;
    private final CompanyPipelineMapper companyPipelineMapper;


    public CandidateComponentImpl(CandidateService candidateService,
                                  JobService jobService,
                                  FileService fileService,
                                  JobMapper jobMapper,
                                  CompanyPipelineService companyPipelineService,
                                  CompanyPipelineMapper companyPipelineMapper) {
        this.candidateService = candidateService;
        this.jobService = jobService;
        this.fileService = fileService;
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
    public ResponseVM<CandidateDTO> updateState(Long id, CandidateState state) throws NotFoundException {
        log.debug("Request to update candidateState via component : {}, {}", id, state);
        CandidateDTO candidateDTO = candidateService.updateState(id, state);
        ResponseVM<CandidateDTO> responseVM = new ResponseVM<>();
        responseVM.setData(candidateDTO);
        responseVM.setInclude(this.createIncluded(candidateDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CandidateDTO> updatePipeline(Long id, Long pipelineId) throws NotFoundException {
        log.debug("Request to update candidatePipeline via component : {}, {}", id, pipelineId);
        CandidateDTO candidateDTO = candidateService.updatePipeline(id,pipelineId);
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
    public Page<ResponseVM<CandidateDTO>> findByFilter(CandidateFilterVM filterVM, Pageable pageable)
        throws NotFoundException {
        log.debug("Request to findAll candidate by filter via component : {}", filterVM);
        Page<CandidateDTO> candidateDTOS = candidateService.findByFilter(filterVM, pageable);
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

        if (candidateDTO.getCandidatePipeline() != null) {
            try {
                CompanyPipelineDTO companyPipelineDTO =
                    companyPipelineService.findOne(candidateDTO.getCandidatePipeline());
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
