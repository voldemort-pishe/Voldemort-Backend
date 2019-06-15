package hr.pishe.domain.entity.jpa;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "subscription")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class SubscriptionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private CompanyEntity company;

    @OneToOne
    @JoinColumn(unique = true)
    private CompanyPlanEntity companyPlan;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "actual_date")
    private ZonedDateTime actualDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public CompanyPlanEntity getCompanyPlan() {
        return companyPlan;
    }

    public void setCompanyPlan(CompanyPlanEntity companyPlan) {
        this.companyPlan = companyPlan;
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
        return "SubscriptionEntity{" +
            "id=" + id +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
