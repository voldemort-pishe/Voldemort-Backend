package io.avand.config;

import io.avand.security.CustomMethodSecurityExpressionHandler;
import io.avand.security.CustomPermissionEvaluator;
import io.avand.service.CompanyMemberService;
import io.avand.service.JobHireTeamService;
import io.avand.service.JobService;
import io.avand.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityPermissionConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private JobHireTeamService jobHireTeamService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyMemberService companyMemberService;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler methodSecurityExpressionHandler = new CustomMethodSecurityExpressionHandler(
            jobHireTeamService,
            jobService,
            permissionService,
            companyMemberService
        );
        methodSecurityExpressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator(permissionService));
        return methodSecurityExpressionHandler;
    }
}
