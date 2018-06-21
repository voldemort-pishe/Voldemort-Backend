package io.avand.service.dto;

import io.avand.domain.enumeration.PermissionAction;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserPermissionDTO implements Serializable {

    @NotNull
    private Long id;

    private PermissionAction action;

    private UserAuthorityDTO userAuthority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionAction getAction() {
        return action;
    }

    public void setAction(PermissionAction action) {
        this.action = action;
    }

    public UserAuthorityDTO getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(UserAuthorityDTO userAuthority) {
        this.userAuthority = userAuthority;
    }

    @Override
    public String toString() {
        return "UserPermissionDTO{" +
            "id=" + id +
            ", action=" + action +
            ", userAuthority=" + userAuthority +
            '}';
    }
}
