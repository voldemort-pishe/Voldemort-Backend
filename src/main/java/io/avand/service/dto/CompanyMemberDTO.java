package io.avand.service.dto;

import java.io.Serializable;
import java.util.List;

public class CompanyMemberDTO implements Serializable {

    private Long id;

    private String department;

    private Long userId;

    private String position;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
            ", department='" + department + '\'' +
            ", userId=" + userId +
            ", position='" + position + '\'' +
            ", companyId=" + companyId +
            '}';
    }
}
