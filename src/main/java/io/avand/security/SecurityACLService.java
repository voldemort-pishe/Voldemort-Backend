package io.avand.security;

import io.avand.domain.entity.jpa.*;
import io.avand.domain.enumeration.ClassType;
import io.avand.repository.jpa.*;
import io.avand.service.dto.*;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityACLService {
    private final CandidateRepository candidateRepository;
    private final CandidateEvaluationCriteriaRepository candidateEvaluationCriteriaRepository;
    private final CandidateMessageRepository candidateMessageRepository;
    private final CandidateScheduleMemberRepository scheduleMemberRepository;
    private final CandidateScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final FeedbackRepository feedbackRepository;
    private final CompanyMemberRepository memberRepository;
    private final CompanyPipelineRepository pipelineRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final JobRepository jobRepository;
    private final JobHireTeamRepository jobHireTeamRepository;
    private final InvoiceRepository invoiceRepository;

    public SecurityACLService(CandidateRepository candidateRepository,
                              CandidateEvaluationCriteriaRepository candidateEvaluationCriteriaRepository,
                              CandidateMessageRepository candidateMessageRepository,
                              CandidateScheduleMemberRepository scheduleMemberRepository,
                              CandidateScheduleRepository scheduleRepository,
                              CommentRepository commentRepository,
                              FeedbackRepository feedbackRepository,
                              CompanyMemberRepository memberRepository,
                              CompanyPipelineRepository pipelineRepository,
                              EvaluationCriteriaRepository evaluationCriteriaRepository,
                              JobRepository jobRepository,
                              JobHireTeamRepository jobHireTeamRepository,
                              InvoiceRepository invoiceRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateEvaluationCriteriaRepository = candidateEvaluationCriteriaRepository;
        this.candidateMessageRepository = candidateMessageRepository;
        this.scheduleMemberRepository = scheduleMemberRepository;
        this.scheduleRepository = scheduleRepository;
        this.commentRepository = commentRepository;
        this.feedbackRepository = feedbackRepository;
        this.memberRepository = memberRepository;
        this.pipelineRepository = pipelineRepository;
        this.evaluationCriteriaRepository = evaluationCriteriaRepository;
        this.jobRepository = jobRepository;
        this.jobHireTeamRepository = jobHireTeamRepository;
        this.invoiceRepository = invoiceRepository;
    }

    boolean isJobMember(Authentication authentication, Long id, ClassType type, PermissionDTO permissionDTO) {
        try {
            Long jobId = null;
            switch (type) {
                case JOB:
                    jobId = id;
                    break;
                case JOB_HIRE_TEAM:
                    jobId = this.findJobByJobHireTeam(id);
                    break;
                case CANDIDATE:
                    jobId = this.findJobIdByCandidate(id);
                    break;
                case CANDIDATE_CRITERIA:
                    jobId = this.findJobByCandidateEvaluationCriteria(id);
                    break;
                case CANDIDATE_MESSAGE:
                    jobId = this.findJobByCandidateMessage(id);
                    break;
                case SCHEDULE_MEMBER:
                    jobId = this.findJobByScheduleMember(id);
                    break;
                case SCHEDULE:
                    jobId = this.findJobBySchedule(id);
                    break;
                case COMMENT:
                    jobId = this.findJobByComment(id);
                    break;
                case FEEDBACK:
                    jobId = this.findJobByFeedback(id);
                    break;
            }
            if (jobId != null) {
                return this.isJobMember(authentication, jobId, permissionDTO);
            } else {
                return false;
            }

        } catch (NotFoundException e) {
            return false;
        }
    }

    private boolean isJobMember(Authentication authentication, Long jobId, PermissionDTO permissionDTO) {
        Optional<String> username = SecurityUtils.getCurrentUserLogin();
        if (username.isPresent()) {
            JobEntity jobEntity = jobRepository.findOne(jobId);
            if (this.checkCompanyMember(jobEntity.getCompany().getId(), username.get())) {
                List<JobHireTeamEntity> jobHireTeamEntities = jobHireTeamRepository
                    .findAllByUser_LoginAndJob_Id(username.get(), jobId);
                List<String> authorities = this.createAuthorityList(authentication);
                for (JobHireTeamEntity jobHireTeamEntity : jobHireTeamEntities) {
                    authorities.add(jobHireTeamEntity.getRole().name());
                }
                return this.checkAuthority(authorities, permissionDTO);
            }
            return false;
        }
        return false;
    }

    boolean isSystemMember(Authentication authentication, Long id, ClassType type, PermissionDTO permissionDTO) {
        try {
            Long companyId = null;
            switch (type) {
                case COMPANY:
                    companyId = id;
                    break;
                case COMPANY_MEMBER:
                    companyId = this.findCompanyByMember(id);
                    break;
                case COMPANY_PIPELINE:
                    companyId = this.findCompanyByPipeline(id);
                    break;
                case CRITERIA:
                    companyId = this.findCompanyByEvaluation(id);
                    break;
                case INVOICE:
                    companyId = this.findCompanyByInvoice(id);
            }
            if (companyId != null) {
                return this.isSystemMember(authentication, companyId, permissionDTO);
            } else {
                return false;
            }
        } catch (NotFoundException e) {
            return false;
        }
    }

    boolean checkTokenAuthority(Authentication authentication, PermissionDTO permissionDTO) {
        List<String> authorities = this.createAuthorityList(authentication);
        return this.checkAuthority(authorities, permissionDTO);
    }

    private boolean isSystemMember(Authentication authentication, Long companyId, PermissionDTO permissionDTO) {
        Optional<String> username = SecurityUtils.getCurrentUserLogin();
        if (username.isPresent()) {
            if (this.checkCompanyMember(companyId, username.get())) {
                List<String> authorities = this.createAuthorityList(authentication);
                return this.checkAuthority(authorities, permissionDTO);
            }
        }
        return false;
    }

    private Long findJobIdByCandidate(Long id) throws NotFoundException {
        CandidateEntity candidateEntity = candidateRepository.findOne(id);
        if (candidateEntity != null) {
            return candidateEntity.getJob().getId();
        } else {
            throw new NotFoundException("Not Found");
        }
    }

    private Long findJobByCandidateEvaluationCriteria(Long id) throws NotFoundException {
        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity =
            candidateEvaluationCriteriaRepository.findOne(id);
        if (candidateEvaluationCriteriaEntity != null) {
            return candidateEvaluationCriteriaEntity.getCandidate().getJob().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findJobByCandidateMessage(Long id) throws NotFoundException {
        CandidateMessageEntity candidateMessageEntity = candidateMessageRepository
            .findOne(id);
        if (candidateMessageEntity != null) {
            return candidateMessageEntity.getCandidate().getJob().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findJobByScheduleMember(Long id) throws NotFoundException {
        CandidateScheduleMemberEntity scheduleMemberEntity = scheduleMemberRepository.findOne(id);
        if (scheduleMemberEntity != null) {
            return scheduleMemberEntity.getCandidateSchedule().getCandidate().getJob().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findJobBySchedule(Long id) throws NotFoundException {
        CandidateScheduleEntity scheduleEntity = scheduleRepository.findOne(id);
        if (scheduleEntity != null) {
            return scheduleEntity.getCandidate().getJob().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findJobByComment(Long id) throws NotFoundException {
        CommentEntity commentEntity = commentRepository.findOne(id);
        if (commentEntity != null) {
            return commentEntity.getCandidate().getJob().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findJobByFeedback(Long id) throws NotFoundException {
        FeedbackEntity feedbackEntity = feedbackRepository.findOne(id);
        if (feedbackEntity != null) {
            return feedbackEntity.getCandidate().getJob().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findJobByJobHireTeam(Long id) throws NotFoundException {
        JobHireTeamEntity jobHireTeamEntity = jobHireTeamRepository.findOne(id);
        if (jobHireTeamEntity != null) {
            return jobHireTeamEntity.getJob().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findCompanyByMember(Long id) throws NotFoundException {
        CompanyMemberEntity companyMemberEntity = memberRepository.findOne(id);
        if (companyMemberEntity != null) {
            return companyMemberEntity.getCompany().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findCompanyByPipeline(Long id) throws NotFoundException {
        CompanyPipelineEntity pipelineEntity = pipelineRepository.findOne(id);
        if (pipelineEntity != null) {
            return pipelineEntity.getCompany().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findCompanyByEvaluation(Long id) throws NotFoundException {
        EvaluationCriteriaEntity criteriaEntity = evaluationCriteriaRepository.findOne(id);
        if (criteriaEntity != null) {
            return criteriaEntity.getCompany().getId();
        } else {
            throw new NotFoundException("NotFound");
        }
    }

    private Long findCompanyByInvoice(Long id) throws NotFoundException{
        InvoiceEntity invoiceEntity = invoiceRepository.findOne(id);
        if (invoiceEntity!=null){
            return invoiceEntity.getCompany().getId();
        }else {
            throw new NotFoundException("NotFound");
        }
    }

    private boolean checkCompanyMember(Long companyId, String username) {
        Optional<CompanyMemberEntity> companyMemberEntityOptional = memberRepository.findByUser_Login(username);
        return companyMemberEntityOptional
            .map(companyMemberEntity -> companyMemberEntity.getCompany().getId().equals(companyId))
            .orElse(false);
    }

    private List<String> createAuthorityList(Authentication authentication) {
        List<String> authorities = new ArrayList<>();
        authentication.getAuthorities().forEach(o2 -> {
            if (!(o2.getAuthority().equals("ROLE_RECRUITER") ||
                o2.getAuthority().equals("ROLE_HIRING_MANAGER") ||
                o2.getAuthority().equals("ROLE_COORDINATOR"))) {
                authorities.add(o2.getAuthority());
            }
        });
        return authorities;
    }

    private boolean checkAuthority(List<String> authorities, PermissionDTO permissionDTO) {
        for (String authority : authorities) {
            for (AuthorityDTO permissionDTOAuthority : permissionDTO.getAuthorities()) {
                if (authority.equals(permissionDTOAuthority.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
