package io.avand.web.rest.vm;

import io.avand.domain.enumeration.CandidateState;
import io.avand.domain.enumeration.CandidateType;

import java.io.Serializable;

public class CandidateFilterVM implements Serializable {
    private CandidateState state;
    private Long pipeline;
    private CandidateType type;
    private Long job;
    private Long company;
    private String search;

    public CandidateState getState() {
        return state;
    }

    public void setState(CandidateState state) {
        this.state = state;
    }

    public Long getPipeline() {
        return pipeline;
    }

    public void setPipeline(Long pipeline) {
        this.pipeline = pipeline;
    }

    public CandidateType getType() {
        return type;
    }

    public void setType(CandidateType type) {
        this.type = type;
    }

    public Long getJob() {
        return job;
    }

    public void setJob(Long job) {
        this.job = job;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "CandidateFilterVM{" +
            "state=" + state +
            ", pipeline=" + pipeline +
            ", type=" + type +
            ", job=" + job +
            ", company=" + company +
            ", search='" + search + '\'' +
            '}';
    }
}
