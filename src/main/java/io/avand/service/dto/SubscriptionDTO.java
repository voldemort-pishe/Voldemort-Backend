package io.avand.service.dto;

import io.avand.domain.enumeration.SubscriptionStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class SubscriptionDTO implements Serializable {

    private Long id;

    private Long userId;

    private String planTitle;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private SubscriptionStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
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

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", planTitle='" + planTitle + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", status=" + status +
            '}';
    }
}
