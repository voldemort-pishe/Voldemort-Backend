package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserAuthorityDTO implements Serializable {

    @NotNull
    private Long id;

    private String authorityName;

    private Set<UserPermissionDTO> userPermissions = new HashSet<>();

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Set<UserPermissionDTO> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Set<UserPermissionDTO> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserAuthorityDTO{" +
            "id=" + id +
            ", authorityName='" + authorityName + '\'' +
            ", userPermissions=" + userPermissions +
            ", user=" + user +
            '}';
    }
}
