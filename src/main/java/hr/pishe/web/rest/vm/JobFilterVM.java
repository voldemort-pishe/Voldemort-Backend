package hr.pishe.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.pishe.domain.enumeration.JobStatus;

import java.io.Serializable;

public class JobFilterVM implements Serializable {
    private String search;
    private JobStatus status;
    private Boolean hireTeam;
    @JsonIgnore
    private Long manager;
    @JsonIgnore
    private Long company;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Boolean getHireTeam() {
        return hireTeam;
    }

    public void setHireTeam(Boolean hireTeam) {
        this.hireTeam = hireTeam;
    }

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "JobFilterVM{" +
            "status=" + status +
            ", hireTeam=" + hireTeam +
            ", company=" + company +
            '}';
    }
}
