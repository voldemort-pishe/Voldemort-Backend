package hr.pishe.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class SubscriptionHistoryDTO implements Serializable {

    private Long id;

    private String planTitle;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "SubscriptionHistoryDTO{" +
            "id=" + id +
            ", planTitle='" + planTitle + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
