package hr.pishe.web.rest;

import hr.pishe.config.ApplicationProperties;
import hr.pishe.domain.enumeration.CandidateState;
import hr.pishe.domain.enumeration.CandidateType;
import hr.pishe.service.*;
import hr.pishe.service.dto.*;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.vm.CareerPageCompanyVM;
import hr.pishe.web.rest.vm.CareerPageJobVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/career-page")
public class CareerPageResource {

    private final Logger log = LoggerFactory.getLogger(CareerPageResource.class);
    private final CompanyService companyService;
    private final ApplicationProperties applicationProperties;
    private final JobService jobService;
    private final CandidateService candidateService;
    private final StorageService storageService;
    private final FileService fileService;

    public CareerPageResource(CompanyService companyService,
                              ApplicationProperties applicationProperties,
                              JobService jobService,
                              CandidateService candidateService,
                              StorageService storageService,
                              FileService fileService) {
        this.companyService = companyService;
        this.applicationProperties = applicationProperties;
        this.jobService = jobService;
        this.candidateService = candidateService;
        this.storageService = storageService;
        this.fileService = fileService;
    }

    @GetMapping("/company/{subDomain}")
    public ResponseEntity getCompanyInfo(@PathVariable("subDomain") String subDomain,
                                         HttpServletResponse response) {
        log.debug("REST Request to get info of company : {}", subDomain);
        try {
            CompanyDTO companyDTO = companyService.findBySubDomain(subDomain);
            CareerPageCompanyVM companyVM = new CareerPageCompanyVM();
            companyVM.setNameFa(companyDTO.getNameFa());
            companyVM.setNameEn(companyDTO.getNameEn());
            companyVM.setDescriptionFa(companyDTO.getDescriptionFa());
            companyVM.setDescriptionEn(companyDTO.getDescriptionEn());
            companyVM.setFilePath(applicationProperties.getBase().getUrl() + "api/file/load/" + companyDTO.getFileId());
            return new ResponseEntity<>(companyVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/job/{subDomain}")
    public ResponseEntity getJobInfo(@PathVariable("subDomain") String subDomain) {
        log.debug("REST Request to get info of job : {}", subDomain);
        List<JobDTO> jobDTOS = jobService.findAllByCompanySubDomain(subDomain);
        List<CareerPageJobVM> jobVMS = new ArrayList<>();
        for (JobDTO jobDTO : jobDTOS) {
            CareerPageJobVM jobVM = new CareerPageJobVM();
            jobVM.setId(jobDTO.getId());
            jobVM.setUniqueId(jobDTO.getUniqueId());
            jobVM.setNameFa(jobDTO.getNameFa());
            jobVM.setDescriptionFa(jobDTO.getDescriptionFa());
            jobVM.setNameEn(jobDTO.getNameEn());
            jobVM.setDescriptionEn(jobDTO.getDescriptionEn());
            jobVM.setLanguage(jobDTO.getLanguage());
            jobVM.setType(jobDTO.getType());
            jobVM.setLocation(jobDTO.getLocation());
            jobVMS.add(jobVM);
        }

        return new ResponseEntity<>(jobVMS, HttpStatus.OK);
    }

    @GetMapping("/job/details/{jobId}/{subDomain}")
    public ResponseEntity getJobDetails(@PathVariable("jobId") String jobId,
                                        @PathVariable("subDomain") String subDomain) {
        log.debug("REST Request to get job details : {}, {}", jobId, subDomain);
        try {
            JobDTO jobDTO = jobService.findByJobUniqueIdAndCompanySubDomain(jobId, subDomain);
            CareerPageJobVM jobVM = new CareerPageJobVM();
            jobVM.setId(jobDTO.getId());
            jobVM.setUniqueId(jobDTO.getUniqueId());
            jobVM.setNameFa(jobDTO.getNameFa());
            jobVM.setDescriptionFa(jobDTO.getDescriptionFa());
            jobVM.setNameEn(jobDTO.getNameEn());
            jobVM.setDescriptionEn(jobDTO.getDescriptionEn());
            jobVM.setLanguage(jobDTO.getLanguage());
            jobVM.setType(jobDTO.getType());
            jobVM.setLocation(jobDTO.getLocation());
            return new ResponseEntity<>(jobVM, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/candidate/{subDomain}")
    public ResponseEntity createCandidate(@RequestBody CareerPageCandidateDTO careerPageCandidateDTO,
                                          @PathVariable("subDomain") String subDomain) {
        log.debug("REST Request to create candidate : {}", subDomain);
        try {

            JobDTO jobDTO = jobService.findByJobUniqueIdAndCompanySubDomain(careerPageCandidateDTO.getJobUniqueId(), subDomain);

            CandidateDTO candidateDTO = new CandidateDTO();
            candidateDTO.setFirstName(careerPageCandidateDTO.getFirstName());
            candidateDTO.setLastName(careerPageCandidateDTO.getLastName());
            candidateDTO.setCellphone(careerPageCandidateDTO.getCellphone());
            candidateDTO.setEmail(careerPageCandidateDTO.getEmail());
            candidateDTO.setState(CandidateState.PENDING);
            candidateDTO.setFileId(careerPageCandidateDTO.getFileId());
            candidateDTO.setJobId(jobDTO.getId());
            candidateDTO.setType(CandidateType.APPLICANT);

            CandidateDTO result = candidateService.save(candidateDTO, subDomain);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    //TODO fix this
    @PostMapping("/file/{subDomain}")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,
                                           @PathVariable("subDomain") String subDomain) {
        try {
            companyService.findBySubDomain(subDomain);
            String fileName = storageService.store(file);
            FileDTO newFile = new FileDTO();
            newFile.setFilename(fileName);
            newFile.setFileType(file.getContentType());
            FileDTO savedFile = fileService.save(newFile);
            return new ResponseEntity<>(savedFile, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

}
