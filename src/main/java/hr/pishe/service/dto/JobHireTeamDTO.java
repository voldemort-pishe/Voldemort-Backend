package hr.pishe.service.dto;

import hr.pishe.domain.enumeration.JobHireTeamRole;

import java.io.Serializable;

public class JobHireTeamDTO extends AbstractAuditingDTO implements Serializable {

    private JobHireTeamRole role;
    private Long userId;
    private Long jobId;

    public JobHireTeamRole getRole() {
        return role;
    }

    public void setRole(JobHireTeamRole role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "JobHireTeamDTO{" +
            "role=" + role +
            ", userId=" + userId +
            ", jobId=" + jobId +
            '}';
    }
}
