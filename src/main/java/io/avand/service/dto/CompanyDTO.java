package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CompanyDTO extends AbstractAuditingDTO implements Serializable {

    private String name;

    private Set<JobDTO> jobs = new HashSet<>();

    private Set<EvaluationCriteriaDTO> evaluationCriteria = new HashSet<>();

    private Set<CompanyPipelineDTO> companyPipelines = new HashSet<>();

    @NotNull
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "name='" + name + '\'' +
            ", jobs=" + jobs +
            ", evaluationCriteria=" + evaluationCriteria +
            ", companyPipelines=" + companyPipelines +
            '}';
    }
}
