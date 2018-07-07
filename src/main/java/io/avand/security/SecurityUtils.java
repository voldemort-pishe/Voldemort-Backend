package io.avand.security;

import io.avand.service.UserService;
import io.avand.service.dto.UserDTO;
import javassist.NotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
@Component
public class SecurityUtils {


    private final UserService userService;

    private SecurityUtils(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

    public Long getCurrentUserId() throws NotFoundException {
        Optional<String> currentUserLogin = getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            Optional<UserDTO> userDTO = userService.findByLogin(currentUserLogin.get());
            if (userDTO.isPresent()) {
                return userDTO.get().getId();
            } else {
                throw new NotFoundException("You Should Login First");
            }
        } else {
            throw new NotFoundException("You Should Login First");
        }
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
            .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
            .orElse(false);
    }
}
