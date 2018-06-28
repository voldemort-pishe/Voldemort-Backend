package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CompanyDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    private Long id;

    private String name;

    private Set<JobDTO> jobs = new HashSet<>();

    private Set<EvaluationCriteriaDTO> evaluationCriteria = new HashSet<>();

    private Set<CompanyPipelineDTO> companyPipelines = new HashSet<>();

    @JsonIgnore
    private UserDTO user;

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

    public Set<JobDTO> getJobs() {
        return jobs;
    }

    public void setJobs(Set<JobDTO> jobs) {
        this.jobs = jobs;
    }

    public Set<EvaluationCriteriaDTO> getEvaluationCriteria() {
        return evaluationCriteria;
    }

    public void setEvaluationCriteria(Set<EvaluationCriteriaDTO> evaluationCriteria) {
        this.evaluationCriteria = evaluationCriteria;
    }

    public Set<CompanyPipelineDTO> getCompanyPipelines() {
        return companyPipelines;
    }

    public void setCompanyPipelines(Set<CompanyPipelineDTO> companyPipelines) {
        this.companyPipelines = companyPipelines;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", jobs=" + jobs +
            ", evaluationCriteria=" + evaluationCriteria +
            ", companyPipelines=" + companyPipelines +
            '}';
    }
}
