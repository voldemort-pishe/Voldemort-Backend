package io.avand.security;

import io.avand.service.CompanyMemberService;
import io.avand.service.JobHireTeamService;
import io.avand.service.JobService;
import io.avand.service.PermissionService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private final JobHireTeamService jobHireTeamService;
    private final JobService jobService;
    private final PermissionService permissionService;
    private final CompanyMemberService companyMemberService;

    public CustomMethodSecurityExpressionHandler(JobHireTeamService jobHireTeamService,
                                                 JobService jobService,
                                                 PermissionService permissionService,
                                                 CompanyMemberService companyMemberService) {
        this.jobHireTeamService = jobHireTeamService;
        this.jobService = jobService;
        this.permissionService = permissionService;
        this.companyMemberService = companyMemberService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
        Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpression root = new CustomMethodSecurityExpression(
                authentication,
                jobHireTeamService,
                jobService,
                companyMemberService,
                permissionService
            );
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(trustResolver);
        return root;
    }

}
