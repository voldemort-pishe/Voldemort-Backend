package io.avand.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;

public class SubscriptionDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long planId;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

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

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", planId=" + planId +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
