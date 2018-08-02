package io.avand.web.rest;

import io.avand.config.ApplicationProperties;
import io.avand.service.CompanyService;
import io.avand.service.JobService;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.JobDTO;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.CareerPageCompanyVM;
import io.avand.web.rest.vm.CareerPageJobVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/career-page")
public class CareerPageResource {

    private final Logger log = LoggerFactory.getLogger(CareerPageResource.class);
    private final CompanyService companyService;
    private final ApplicationProperties applicationProperties;
    private final JobService jobService;

    public CareerPageResource(CompanyService companyService,
                              ApplicationProperties applicationProperties,
                              JobService jobService) {
        this.companyService = companyService;
        this.applicationProperties = applicationProperties;
        this.jobService = jobService;
    }

    @GetMapping("/company/{subDomain}")
    public ResponseEntity getCompanyInfo(@PathVariable("subDomain") String subDomain) {
        log.debug("REST Request to get info of company : {}", subDomain);
        try {
            CompanyDTO companyDTO = companyService.findBySubDomain(subDomain);
            CareerPageCompanyVM companyVM = new CareerPageCompanyVM();
            companyVM.setNameFa(companyDTO.getNameFa());
            companyVM.setNameEn(companyDTO.getNameEn());
            companyVM.setDescriptionFa(companyDTO.getDescriptionFa());
            companyVM.setDescriptionEn(companyDTO.getDescriptionEn());
            companyVM.setFilePath(applicationProperties.getBase().getUrl() + "/api/file/load/"+companyDTO.getFileId());
            return new ResponseEntity<>(companyVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/job/{subDomain}")
    public ResponseEntity getJobInfo(@PathVariable("subDomain") String subDomain){
        log.debug("REST Request to get info of job : {}",subDomain);
        List<JobDTO> jobDTOS = jobService.findAllByCompanySubDomain(subDomain);
        List<CareerPageJobVM> jobVMS = new ArrayList<>();
        for (JobDTO jobDTO : jobDTOS) {
            CareerPageJobVM jobVM = new CareerPageJobVM();
            jobVM.setName(jobDTO.getName());
            jobVM.setType(jobDTO.getType());
            jobVM.setLocation(jobDTO.getLocation());
            jobVMS.add(jobVM);
        }

        return new ResponseEntity<>(jobVMS,HttpStatus.OK);
    }

}
