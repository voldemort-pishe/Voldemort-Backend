package hr.pishe.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.pishe.domain.enumeration.ScheduleStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A CandidateScheduleEntity.
 */
@Entity
@Table(name = "candidate_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CandidateScheduleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "candidateSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private List<CandidateScheduleMemberEntity> member = new ArrayList<>();

    @ManyToOne
    private CandidateEntity candidate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<CandidateScheduleMemberEntity> getMember() {
        return member;
    }

    public void setMember(List<CandidateScheduleMemberEntity> member) {
        this.member = member;
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
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", location='" + location + '\'' +
            ", status=" + status +
            ", description='" + description + '\'' +
            ", candidate=" + candidate +
            '}';
    }
}
