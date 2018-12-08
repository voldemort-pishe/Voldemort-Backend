package io.avand.web.rest.component.impl;

import io.avand.service.CompanyService;
import io.avand.service.JobService;
import io.avand.service.UserService;
import io.avand.service.dto.JobDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.CompanyMapper;
import io.avand.service.mapper.UserMapper;
import io.avand.web.rest.component.JobComponent;
import io.avand.web.rest.util.PageMaker;
import io.avand.web.rest.vm.JobFilterVM;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JobComponentImpl implements JobComponent {

    private final Logger log = LoggerFactory.getLogger(JobComponentImpl.class);
    private final JobService jobService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public JobComponentImpl(JobService jobService,
                            CompanyService companyService,
                            CompanyMapper companyMapper) {
        this.jobService = jobService;
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @Override
    public ResponseVM<JobDTO> save(JobDTO jobDTO) throws NotFoundException {
        log.debug("Request to save jobDTO via component : {}", jobDTO);
        jobDTO = jobService.save(jobDTO);
        ResponseVM<JobDTO> responseVM = new ResponseVM<>();
        responseVM.setData(jobDTO);
        responseVM.setInclude(this.createIncluded(jobDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<JobDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find jobDTO by id via component : {}", id);
        JobDTO jobDTO = jobService.findById(id);
        ResponseVM<JobDTO> responseVM = new ResponseVM<>();
        responseVM.setData(jobDTO);
        responseVM.setInclude(this.createIncluded(jobDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<JobDTO>> findAllByFilter(JobFilterVM filterVM, Pageable pageable) throws NotFoundException {
        log.debug("Request to findAll jobDTO by filter via component : {}", filterVM);
        Page<JobDTO> jobDTOs = jobService.findAllByFilter(pageable, filterVM);
        List<ResponseVM<JobDTO>> responseVMS = new ArrayList<>();
        for (JobDTO jobDTO : jobDTOs) {
            ResponseVM<JobDTO> responseVM = new ResponseVM<>();
            responseVM.setData(jobDTO);
            responseVM.setInclude(this.createIncluded(jobDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, jobDTOs);
    }

    private Map<String, Object> createIncluded(JobDTO jobDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        included.put("company", companyMapper.dtoToVm(companyService.findById(jobDTO.getCompanyId())));

        return included;
    }
}
