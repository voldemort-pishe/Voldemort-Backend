package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.CandidateState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TalentPoolDTO implements Serializable {

    @NotNull
    private Long id;

    private String firstName;

    private String lastName;

    private Long fileId;

    private CandidateState state;

    private String cellphone;

    private String email;

    @NotNull
    private Long userId;

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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public CandidateState getState() {
        return state;
    }

    public void setState(CandidateState state) {
        this.state = state;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TalentPoolDTO{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", fileId=" + fileId +
            ", state=" + state +
            ", cellphone='" + cellphone + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
