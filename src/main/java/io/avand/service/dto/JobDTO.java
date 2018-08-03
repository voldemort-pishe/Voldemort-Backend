package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.JobType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class JobDTO extends AbstractAuditingDTO implements Serializable {

    private String name;

    private JobType type;

    private String location;

    @JsonIgnore
    private Set<CandidateDTO> candidate = new HashSet<>();

    @NotNull
    private Long companyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
            ", type=" + type +
            ", location='" + location + '\'' +
            ", candidate=" + candidate +
            '}';
    }
}
