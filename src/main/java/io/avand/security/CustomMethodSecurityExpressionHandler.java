package io.avand.security;

import io.avand.service.PermissionService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private final SecurityACLService securityACLService;
    private final PermissionService permissionService;

    public CustomMethodSecurityExpressionHandler(SecurityACLService securityACLService, PermissionService permissionService) {
        this.securityACLService = securityACLService;
        this.permissionService = permissionService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
        Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpression root = new CustomMethodSecurityExpression(
            authentication,
            securityACLService,
            permissionService);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(trustResolver);
        return root;
    }

}
