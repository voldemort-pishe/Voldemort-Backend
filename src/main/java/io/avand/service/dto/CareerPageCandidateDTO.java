package io.avand.service.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CareerPageCandidateDTO extends AbstractAuditingDTO implements Serializable {
    
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;
    
    @NotNull
    private String cellphone;
    
    @NotNull
    private String email;

    @NotNull
    private Long fileId;

    @NotNull
    private String jobUniqueId;
    
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
    
    public String getJobUniqueId() {
        return jobUniqueId;
    }
    
    public void setJobUniqueId(String jobUniqueId) {
        this.jobUniqueId = jobUniqueId;
    }
    
    @Override
    public String toString() {
        return "CareerPageCandidateDTO{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", cellphone='" + cellphone + '\'' +
            ", email='" + email + '\'' +
            ", fileId=" + fileId +
            ", jobUniqueId='" + jobUniqueId + '\'' +
            '}';
    }
}
