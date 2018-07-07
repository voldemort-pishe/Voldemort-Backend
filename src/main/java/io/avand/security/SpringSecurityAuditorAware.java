package io.avand.security;

import io.avand.config.Constants;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private final SecurityUtils securityUtils;

    public SpringSecurityAuditorAware(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public String getCurrentAuditor() {
        return securityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT);
    }
}
