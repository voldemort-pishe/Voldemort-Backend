package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class JobDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    private Long id;

    private String name;

    private CandidateDTO candidate;

    private CompanyDTO company;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "JobDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", candidate=" + candidate +
            ", company=" + company +
            '}';
    }
}
