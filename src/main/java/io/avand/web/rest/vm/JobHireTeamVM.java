package io.avand.web.rest.vm;

import io.avand.service.dto.JobHireTeamDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class JobHireTeamVM implements Serializable {
    private List<JobHireTeamDTO> teams;
    @NotNull
    private Long jobId;

    public List<JobHireTeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<JobHireTeamDTO> teams) {
        this.teams = teams;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "JobHireTeamVM{" +
            "teams=" + teams +
            '}';
    }
}
