package hr.pishe.web.rest.vm;

import java.io.Serializable;

public class CompanyMemberFilterVM implements Serializable {
    private String email;
    private Long company;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "CompanyMemberFilterVM{" +
            "email='" + email + '\'' +
            ", company=" + company +
            '}';
    }
}
