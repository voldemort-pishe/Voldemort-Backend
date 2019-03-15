package hr.pishe.service.dto;

import hr.pishe.domain.enumeration.CandidateScheduleMemberStatus;

import java.io.Serializable;

public class CandidateScheduleMemberDTO extends AbstractAuditingDTO implements Serializable {
    private CandidateScheduleMemberStatus status;
    private Long userId;
    private Long candidateScheduleId;

    public CandidateScheduleMemberStatus getStatus() {
        return status;
    }

    public void setStatus(CandidateScheduleMemberStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCandidateScheduleId() {
        return candidateScheduleId;
    }

    public void setCandidateScheduleId(Long candidateScheduleId) {
        this.candidateScheduleId = candidateScheduleId;
    }

    @Override
    public String toString() {
        return "CandidateScheduleMemberDTO{" +
            "status=" + status +
            ", userId=" + userId +
            ", candidateScheduleId=" + candidateScheduleId +
            '}';
    }
}
