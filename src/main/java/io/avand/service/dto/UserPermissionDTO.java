package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.PermissionAction;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserPermissionDTO implements Serializable {

    @NotNull
    private Long id;

    private PermissionAction action;

    @NotNull
    private Long userAuthorityId;

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

    public Long getUserAuthorityId() {
        return userAuthorityId;
    }

    public void setUserAuthorityId(Long userAuthorityId) {
        this.userAuthorityId = userAuthorityId;
    }

    @Override
    public String toString() {
        return "UserPermissionDTO{" +
            "id=" + id +
            ", action=" + action +
            '}';
    }
}
