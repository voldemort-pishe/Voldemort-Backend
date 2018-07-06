package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class JobDTO extends AbstractAuditingDTO implements Serializable {

    private String name;

    private Set<CandidateDTO> candidate = new HashSet<>();

    @NotNull
    private Long companyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CandidateDTO> getCandidate() {
        return candidate;
    }

    public void setCandidate(Set<CandidateDTO> candidate) {
        this.candidate = candidate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "JobDTO{" +
            "name='" + name + '\'' +
            ", candidate=" + candidate +
            '}';
    }
}
