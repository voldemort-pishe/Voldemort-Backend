package io.avand.security;

import io.avand.domain.enumeration.ClassType;
import io.avand.domain.enumeration.PermissionAccess;
import io.avand.service.PermissionService;
import io.avand.service.dto.PermissionDTO;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;


public class CustomMethodSecurityExpression extends SecurityExpressionRoot
    implements MethodSecurityExpressionOperations {

    private final SecurityACLService securityACLService;
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
                                          SecurityACLService securityACLService,
                                          PermissionService permissionService) {
        super(authentication);
        this.securityACLService = securityACLService;
        this.permissionService = permissionService;
    }

    public boolean isMember(Long id, String type, String permission) {
        PermissionDTO permissionDTO = permissionService.findByAccess(PermissionAccess.valueOf(permission));
        if (permissionDTO != null) {
            if (this.isSystemType(type)) {
                return securityACLService.isSystemMember(authentication, id, ClassType.valueOf(type), permissionDTO);
            } else {
                return securityACLService.isJobMember(authentication, id, ClassType.valueOf(type), permissionDTO);
            }
        } else {
            return false;
        }
    }

    private boolean isSystemType(String type) {
        switch (ClassType.valueOf(type)) {
            case COMPANY:
            case COMPANY_MEMBER:
            case COMPANY_PIPELINE:
            case CRITERIA:
            case INVOICE:
                return true;
        }
        return false;
    }
}
