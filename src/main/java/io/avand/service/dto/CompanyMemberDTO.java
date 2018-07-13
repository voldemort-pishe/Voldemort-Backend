package io.avand.service.dto;

import java.io.Serializable;

public class CompanyMemberDTO implements Serializable {

    private Long id;

    private String userEmail;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyMemberDTO{" +
            "id=" + id +
            ", userEmail='" + userEmail + '\'' +
            ", companyId=" + companyId +
            '}';
    }
}
