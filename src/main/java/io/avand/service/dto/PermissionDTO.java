package io.avand.service.dto;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.domain.enumeration.PermissionType;

import java.io.Serializable;

public class PermissionDTO implements Serializable {

    private Long id;
    private PermissionType type;
    private PermissionAccess access;
    private Long authorityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }

    public PermissionAccess getAccess() {
        return access;
    }

    public void setAccess(PermissionAccess access) {
        this.access = access;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
            "id=" + id +
            ", type=" + type +
            ", access=" + access +
            ", authorityId=" + authorityId +
            '}';
    }
}
