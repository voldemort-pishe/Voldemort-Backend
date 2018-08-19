package io.avand.web.rest.component.impl;

import io.avand.service.CompanyPipelineService;
import io.avand.service.CompanyService;
import io.avand.service.dto.CompanyPipelineDTO;
import io.avand.service.mapper.CompanyMapper;
import io.avand.web.rest.component.CompanyPipelineComponent;
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
public class CompanyPipelineComponentImpl implements CompanyPipelineComponent {

    private final Logger log = LoggerFactory.getLogger(CompanyPipelineComponentImpl.class);
    private final CompanyPipelineService companyPipelineService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyPipelineComponentImpl(CompanyPipelineService companyPipelineService,
                                        CompanyService companyService,
                                        CompanyMapper companyMapper) {
        this.companyPipelineService = companyPipelineService;
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @Override
    public ResponseVM<CompanyPipelineDTO> save(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException {
        log.debug("Request to save companyPipelineDTO via component : {}", companyPipelineDTO);
        companyPipelineDTO = companyPipelineService.save(companyPipelineDTO);
        ResponseVM<CompanyPipelineDTO> responseVM = new ResponseVM<>();
        responseVM.setData(companyPipelineDTO);
        responseVM.setInclude(this.createIncluded(companyPipelineDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CompanyPipelineDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find companyPipelineDTO via component");
        CompanyPipelineDTO companyPipelineDTO = companyPipelineService.findOne(id);
        ResponseVM<CompanyPipelineDTO> responseVM = new ResponseVM<>();
        responseVM.setData(companyPipelineDTO);
        responseVM.setInclude(this.createIncluded(companyPipelineDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<CompanyPipelineDTO>> findAllByCompany(Long companyId, Pageable pageable) throws NotFoundException {
        Page<CompanyPipelineDTO> companyPipelineDTOS = companyPipelineService.getAllByCompanyId(companyId, pageable);
        List<ResponseVM<CompanyPipelineDTO>> responseVMS = new ArrayList<>();
        for (CompanyPipelineDTO companyPipelineDTO : companyPipelineDTOS) {
            ResponseVM<CompanyPipelineDTO> responseVM = new ResponseVM<>();
            responseVM.setData(companyPipelineDTO);
            responseVM.setInclude(this.createIncluded(companyPipelineDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, companyPipelineDTOS);
    }

    private Map<String, Object> createIncluded(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();
        included.put("company", companyMapper.dtoToVm(companyService.findById(companyPipelineDTO.getCompanyId())));
        return included;
    }
}
