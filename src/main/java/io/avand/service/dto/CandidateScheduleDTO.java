package io.avand.service.dto;

import io.avand.domain.enumeration.ScheduleStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class CandidateScheduleDTO  implements Serializable {

    @NotNull
    private Long id;

    private Long owner;

    private ZonedDateTime scheduleDate;

//    private ScheduleStatus status;
//
//    private String description;

    @NotNull
    private Long candidateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public ZonedDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(ZonedDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

//    public ScheduleStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(ScheduleStatus status) {
//        this.status = status;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

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
            ", owner=" + owner +
            ", scheduleDate=" + scheduleDate +
//            ", status=" + status +
//            ", description='" + description + '\'' +
            ", candidateId=" + candidateId +
            '}';
    }
}
