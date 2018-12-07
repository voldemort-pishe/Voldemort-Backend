package io.avand.domain.entity.jpa;

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
    private UserEntity user;

    @OneToOne
    @JoinColumn(unique = true)
    private UserPlanEntity userPlan;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserPlanEntity getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlanEntity userPlan) {
        this.userPlan = userPlan;
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
        return "SubscriptionEntity{" +
            "id=" + id +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
