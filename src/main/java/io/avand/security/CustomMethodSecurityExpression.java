package io.avand.security;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.service.CompanyMemberService;
import io.avand.service.JobHireTeamService;
import io.avand.service.JobService;
import io.avand.service.PermissionService;
import io.avand.service.dto.*;
import javassist.NotFoundException;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomMethodSecurityExpression extends SecurityExpressionRoot
    implements MethodSecurityExpressionOperations {


    private final JobHireTeamService jobHireTeamService;
    private final JobService jobService;
    private final CompanyMemberService companyMemberService;
    private final PermissionService permissionService;

    @Override
    public void setFilterObject(Object o) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object o) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }

    public CustomMethodSecurityExpression(Authentication authentication,
                                          JobHireTeamService jobHireTeamService,
                                          JobService jobService,
                                          CompanyMemberService companyMemberService,
                                          PermissionService permissionService) {
        super(authentication);
        this.jobHireTeamService = jobHireTeamService;
        this.jobService = jobService;
        this.companyMemberService = companyMemberService;
        this.permissionService = permissionService;
    }

    public boolean isMember(Long jobId, String permission) {
        Optional<String> username = SecurityUtils.getCurrentUserLogin();
        if (jobId != null && username.isPresent()) {
            try {
                JobDTO jobDTO = jobService.findById(jobId);
                Optional<CompanyMemberDTO> companyMemberDTO = companyMemberService.findByLogin(username.get());
                if (companyMemberDTO.isPresent()) {
                    if (companyMemberDTO.get().getCompanyId().equals(jobDTO.getCompanyId())) {
                        List<JobHireTeamDTO> jobHireTeamDTOS = jobHireTeamService.findAllByUserLoginAndJobId(username.get(), jobId);
                        List<String> authorities = new ArrayList<>();
                        authentication.getAuthorities().forEach(o2 -> {
                            if (!(o2.getAuthority().equals("ROLE_RECRUITER") ||
                                o2.getAuthority().equals("ROLE_HIRING_MANAGER") ||
                                o2.getAuthority().equals("ROLE_COORDINATOR"))) {
                                authorities.add(o2.getAuthority());
                            }
                        });
                        for (JobHireTeamDTO jobHireTeamDTO : jobHireTeamDTOS) {
                            authorities.add(jobHireTeamDTO.getRole().name());
                        }
                        PermissionDTO permissionDTO = permissionService.findByAccess(PermissionAccess.valueOf(permission));
                        for (String authority : authorities) {
                            for (AuthorityDTO permissionDTOAuthority : permissionDTO.getAuthorities()) {
                                if (authority.equals(permissionDTOAuthority.getName())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } catch (NotFoundException e) {
                return false;
            }
        }
        return false;
    }
}
