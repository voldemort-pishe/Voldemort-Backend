package io.avand.security;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.service.PermissionService;
import io.avand.service.dto.AuthorityDTO;
import io.avand.service.dto.PermissionDTO;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final PermissionService permissionService;

    public CustomPermissionEvaluator(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        PermissionDTO permissionDTO = permissionService.findByAccess(PermissionAccess.valueOf(o1.toString()));
        List<String> authorities = new ArrayList<>();
        authentication.getAuthorities().forEach(o2 -> {
            if (!(o2.getAuthority().equals("ROLE_RECRUITER") ||
                o2.getAuthority().equals("ROLE_HIRING_MANAGER") ||
                o2.getAuthority().equals("ROLE_COORDINATOR"))) {
                authorities.add(o2.getAuthority());
            }
        });
        for (String authority : authorities) {
            for (AuthorityDTO permissionDTOAuthority : permissionDTO.getAuthorities()) {
                if (authority.equals(permissionDTOAuthority.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

}
