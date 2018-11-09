package io.avand.domain.entity.jpa;

import io.avand.domain.enumeration.ScheduleStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CandidateScheduleEntity.
 */
@Entity
@Table(name = "candidate_schedule_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CandidateScheduleEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner")
    private Long owner;

    @Column(name = "schedule_date")
    private ZonedDateTime scheduleDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CandidateEntity candidate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public CandidateScheduleEntity owner(Long owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public ZonedDateTime getScheduleDate() {
        return scheduleDate;
    }

    public CandidateScheduleEntity scheduleDate(ZonedDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    public void setScheduleDate(ZonedDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
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

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public CandidateScheduleEntity candidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
        return this;
    }

    public void setCandidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateScheduleEntity candidateScheduleEntity = (CandidateScheduleEntity) o;
        if (candidateScheduleEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidateScheduleEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidateScheduleEntity{" +
            "id=" + id +
            ", owner=" + owner +
            ", scheduleDate=" + scheduleDate +
            ", status=" + status +
            ", description='" + description + '\'' +
            '}';
    }
}
