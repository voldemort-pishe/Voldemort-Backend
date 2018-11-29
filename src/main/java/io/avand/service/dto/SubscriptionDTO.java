package io.avand.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;

public class SubscriptionDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long userPlanId;

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

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
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
            ", userPlanId=" + userPlanId +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
