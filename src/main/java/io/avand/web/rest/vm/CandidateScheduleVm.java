package io.avand.web.rest.vm;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class CandidateScheduleVm implements Serializable {

    private Long id;
    private Long extraId;
    private String fullName;
    private String cellphone;
    private String email;
    private ZonedDateTime scheduleTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExtraId() {
        return extraId;
    }

    public void setExtraId(Long extraId) {
        this.extraId = extraId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(ZonedDateTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @Override
    public String toString() {
        return "CandidateScheduleVm{" +
            "id=" + id +
            ", extraId=" + extraId +
            ", fullName='" + fullName + '\'' +
            ", cellphone='" + cellphone + '\'' +
            ", email='" + email + '\'' +
            ", scheduleTime=" + scheduleTime +
            '}';
    }
}
