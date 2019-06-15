package hr.pishe.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;

public class SubscriptionDTO implements Serializable {

    private Long id;

    private Long companyId;

    private Long companyPlanId;

    private ZonedDateTime startDate;

    private ZonedDateTime actualDate;

    private ZonedDateTime endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyPlanId() {
        return companyPlanId;
    }

    public void setCompanyPlanId(Long companyPlanId) {
        this.companyPlanId = companyPlanId;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getActualDate() {
        return actualDate;
    }

    public void setActualDate(ZonedDateTime actualDate) {
        this.actualDate = actualDate;
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
            ", companyId=" + companyId +
            ", companyPlanId=" + companyPlanId +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
