package io.avand.config;

import io.avand.security.CustomMethodSecurityExpressionHandler;
import io.avand.security.SecurityACLService;
import io.avand.security.SecurityUtils;
import io.avand.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityPermissionConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SecurityACLService securityACLService;
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomMethodSecurityExpressionHandler(securityACLService, permissionService, securityUtils);
    }
}
