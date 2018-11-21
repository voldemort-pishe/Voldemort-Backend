package io.avand.web.rest.vm;

import io.avand.service.dto.JobHireTeamDTO;

import java.io.Serializable;
import java.util.List;

public class JobHireTeamVM implements Serializable {
    private List<JobHireTeamDTO> teams;

    public List<JobHireTeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<JobHireTeamDTO> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "JobHireTeamVM{" +
            "teams=" + teams +
            '}';
    }
}
