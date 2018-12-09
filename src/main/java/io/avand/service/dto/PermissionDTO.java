package io.avand.service.dto;

import io.avand.domain.enumeration.PermissionAccess;

import java.io.Serializable;
import java.util.Set;

public class PermissionDTO implements Serializable {

    private Long id;
    private PermissionAccess access;
    private Set<AuthorityDTO> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionAccess getAccess() {
        return access;
    }

    public void setAccess(PermissionAccess access) {
        this.access = access;
    }

    public Set<AuthorityDTO> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityDTO> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
            "id=" + id +
            ", access=" + access +
            '}';
    }
}
