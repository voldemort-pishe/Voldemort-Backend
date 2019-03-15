package hr.pishe.web.rest.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAuthorityVM implements Serializable {
    private Long id;
    private String authorityName;
    private Long userId;
    private List<String> permissions = new ArrayList<>();

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserAuthorityVM{" +
            "id=" + id +
            ", authorityName='" + authorityName + '\'' +
            ", userId=" + userId +
            ", permissions=" + permissions +
            '}';
    }
}
