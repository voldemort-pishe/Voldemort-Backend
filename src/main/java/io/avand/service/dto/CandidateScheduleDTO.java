package io.avand.service.dto;

import io.avand.domain.entity.jpa.CandidateScheduleMemberEntity;
import io.avand.domain.enumeration.ScheduleStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class CandidateScheduleDTO implements Serializable {

    @NotNull
    private Long id;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private String location;

    private ScheduleStatus status;

    private String description;

    private Set<CandidateScheduleMemberEntity> member = new HashSet<>();

    @NotNull
    private Long candidateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CandidateScheduleMemberEntity> getMember() {
        return member;
    }

    public void setMember(Set<CandidateScheduleMemberEntity> member) {
        this.member = member;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "CandidateScheduleDTO{" +
            "id=" + id +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", location='" + location + '\'' +
            ", status=" + status +
            ", description='" + description + '\'' +
            ", candidateId=" + candidateId +
            '}';
    }
}
