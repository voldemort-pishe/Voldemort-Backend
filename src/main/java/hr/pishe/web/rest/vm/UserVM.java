package hr.pishe.web.rest.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserVM implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String cellphone;
    private String email;
    private Long fileId;
    private List<UserAuthorityVM> authorities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public List<UserAuthorityVM> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<UserAuthorityVM> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserVM{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", cellphone='" + cellphone + '\'' +
            ", email='" + email + '\'' +
            ", fileId=" + fileId +
            ", authorities=" + authorities +
            '}';
    }
}
