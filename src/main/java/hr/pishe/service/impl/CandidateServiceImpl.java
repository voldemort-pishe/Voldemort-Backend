package hr.pishe.service.impl;

import hr.pishe.aop.event.CustomEvent;
import hr.pishe.domain.entity.jpa.*;
import hr.pishe.domain.enumeration.CandidateState;
import hr.pishe.domain.enumeration.CandidateType;
import hr.pishe.domain.enumeration.EventType;
import hr.pishe.repository.jpa.*;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.CandidateService;
import hr.pishe.service.dto.CandidateDTO;
import hr.pishe.service.mapper.CandidateMapper;
import hr.pishe.web.rest.vm.CandidateFilterVM;
import hr.pishe.web.specification.CandidateSpecification;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final FileRepository fileRepository;
    private final CandidateMapper candidateMapper;
    private final CandidateSpecification specification;
    private final SecurityUtils securityUtils;
    private final ApplicationEventPublisher eventPublisher;
    private final CompanyPipelineRepository companyPipelineRepository;
    private final CompanyRepository companyRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                JobRepository jobRepository,
                                FileRepository fileRepository,
                                CandidateMapper candidateMapper,
                                CandidateSpecification specification,
                                SecurityUtils securityUtils,
                                ApplicationEventPublisher eventPublisher,
                                CompanyPipelineRepository companyPipelineRepository, CompanyRepository companyRepository) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.fileRepository = fileRepository;
        this.candidateMapper = candidateMapper;
        this.specification = specification;
        this.securityUtils = securityUtils;
        this.eventPublisher = eventPublisher;
        this.companyPipelineRepository = companyPipelineRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO) throws NotFoundException {
        log.debug("Request to save candidate : {}", candidateDTO);
        JobEntity jobEntity = jobRepository.findOne(candidateDTO.getJobId());
        if (jobEntity != null) {
            FileEntity fileEntity = fileRepository.findOne(candidateDTO.getFileId());
            if (fileEntity != null) {
                // TODO: handle if pipeline is null (it's a valid state)
                CompanyPipelineEntity pipelineEntity = companyPipelineRepository.findOne(candidateDTO.getCandidatePipeline());
                if (pipelineEntity != null) {
                    CandidateEntity candidateEntity = candidateMapper.toEntity(candidateDTO);
                    candidateEntity.setJob(jobEntity);
                    candidateEntity.setFile(fileEntity);
                    candidateEntity.setCandidatePipeline(pipelineEntity);
                    candidateEntity = candidateRepository.save(candidateEntity);

                    if (candidateDTO.getId() == null) {

                        Long userId = securityUtils.getCurrentUserId();

                        for (JobHireTeamEntity jobHireTeamEntity : jobEntity.getJobHireTeam()) {
                            if (!jobHireTeamEntity.getUser().getId().equals(userId)) {
                                CustomEvent customEvent = new CustomEvent(this);
                                customEvent.setTitle(candidateEntity.getFirstName() + " " + candidateEntity.getLastName());
                                customEvent.setDescription(String.format("%s جدید برای شغل %s", candidateEntity.getType() == CandidateType.APPLICANT ? "کارجو" : "فرد مستعد", jobEntity.getNameFa()));
                                customEvent.setOwner(jobHireTeamEntity.getUser().getId());
                                customEvent.setType(EventType.ALARM);
                                customEvent.setExtra(candidateEntity.getId().toString());
                                eventPublisher.publishEvent(customEvent);
                            }
                        }
                    }
                    return candidateMapper.toDto(candidateEntity);
                } else {
                    throw new NotFoundException("Company Pipeline Not Available");
                }
            } else {
                throw new NotFoundException("File Not Available");
            }
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO, String companySubDomain, Long companyId) throws NotFoundException {
        log.debug("Request to save candidate by subDomain : {}, {}", candidateDTO, companySubDomain);
        JobEntity jobEntity = jobRepository.findOne(candidateDTO.getJobId());
        if (jobEntity != null) {
            if (jobEntity.getCompany().getSubDomain().equals(companySubDomain)) {
                FileEntity fileEntity = fileRepository.findOne(candidateDTO.getFileId());
                if (fileEntity != null) {
                    CompanyEntity company = companyRepository.findById(companyId)
                        .orElseThrow(() -> new NotFoundException("CompanyPipeline Not Found"));

                    CandidateEntity candidateEntity = candidateMapper.toEntity(candidateDTO);
                    candidateEntity.setType(CandidateType.APPLICANT);
                    candidateEntity.setState(CandidateState.PENDING);
                    candidateEntity.setJob(jobEntity);
                    candidateEntity.setFile(fileEntity);
                    candidateEntity.setCandidatePipeline(null);
                    candidateEntity = candidateRepository.save(candidateEntity);

                    for (JobHireTeamEntity jobHireTeamEntity : jobEntity.getJobHireTeam()) {
                        CustomEvent customEvent = new CustomEvent(this);
                        customEvent.setTitle(candidateEntity.getFirstName() + " " + candidateEntity.getLastName());
                        customEvent.setDescription(String.format("کارجو جدید برای شغل %s", jobEntity.getNameFa()));
                        customEvent.setOwner(jobHireTeamEntity.getUser().getId());
                        customEvent.setType(EventType.ALARM);
                        customEvent.setExtra(candidateEntity.getId().toString());
                        eventPublisher.publishEvent(customEvent);
                    }

                    return candidateMapper.toDto(candidateEntity);
                } else {
                    throw new NotFoundException("File Not Available");
                }
            } else {
                throw new SecurityException("You Don't Have Access To Create Candidate For This Company");
            }
        } else {
            throw new NotFoundException("Job Not Available");
        }
    }

    @Override
    public CandidateDTO updateState(Long id, CandidateState state, Long pipelineId) throws NotFoundException {
        log.debug("Request to update candidateState : {}, {}", id, state);
        CandidateEntity candidateEntity = candidateRepository.findOne(id);
        if (Objects.isNull(candidateEntity))
            throw new NotFoundException("کاندیدای مورد نظر پیدا نشد.");

        candidateEntity.setState(state);
        CompanyPipelineEntity pipelineEntity = null;
        if (state == CandidateState.IN_PROCESS) {
            if (Objects.isNull(pipelineId))
                throw new IllegalStateException("برای تغییر وضعیت کاندیدای در جریان باید فرآیند انتخاب شود.");

            pipelineEntity = companyPipelineRepository.findOne(pipelineId);
            if (Objects.isNull(pipelineEntity))
                throw new NotFoundException("فرآیند انتخاب شده یافت نشد.");

            candidateEntity.setCandidatePipeline(pipelineEntity);
        } else {
            candidateEntity.setCandidatePipeline(null);
        }
        candidateEntity = candidateRepository.save(candidateEntity);

        Long userId = securityUtils.getCurrentUserId();

        for (JobHireTeamEntity jobHireTeamEntity : candidateEntity.getJob().getJobHireTeam()) {
            if (jobHireTeamEntity.getUser().getId().equals(userId))
                continue;

            CustomEvent customEvent = new CustomEvent(this);
            customEvent.setTitle(candidateEntity.getFirstName() + " " + candidateEntity.getLastName());
            String desc;
            if (Objects.nonNull(pipelineEntity))
                desc = String.format("تغییر فرآیند استخدام کارجو به %s", pipelineEntity.getTitle());
            else
                desc = String.format("تغییر وضعیت کارجو به %s", state.toString());
            customEvent.setDescription(desc);
            customEvent.setOwner(jobHireTeamEntity.getUser().getId());
            customEvent.setType(EventType.ALARM);
            customEvent.setExtra(candidateEntity.getId().toString());
            eventPublisher.publishEvent(customEvent);
        }

        return candidateMapper.toDto(candidateEntity);
    }

    @Override
    public CandidateDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find candidate by id : {}", id);
        CandidateEntity candidateEntity =
            candidateRepository.findByIdAndJob_Company_Id(id, securityUtils.getCurrentCompanyId());
        if (candidateEntity != null) {
            return candidateMapper.toDto(candidateEntity);
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public Page<CandidateDTO> findByFilter(CandidateFilterVM filterVM, Pageable pageable)
        throws NotFoundException {
        if (filterVM == null) {
            filterVM = new CandidateFilterVM();
        }
        filterVM.setCompany(securityUtils.getCurrentCompanyId());
        return candidateRepository
            .findAll(specification.getFilter(filterVM), pageable)
            .map(candidateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete candidate by id : {}", id);
        candidateRepository.delete(id);
    }
}
