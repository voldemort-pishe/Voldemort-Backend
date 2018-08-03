package io.avand.service.dto;

import java.io.Serializable;
import java.util.List;

public class CompanyMemberDTO implements Serializable {

    private Long id;

    private List<String> userEmails;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(List<String> userEmails) {
        this.userEmails = userEmails;
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
            ", userEmails=" + userEmails +
            ", companyId=" + companyId +
            '}';
    }
}
