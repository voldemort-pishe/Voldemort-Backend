package io.avand.domain.entity.jpa;

import io.avand.domain.enumeration.CandidateScheduleMemberStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "candidate_schedule_member_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CandidateScheduleMemberEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CandidateScheduleMemberStatus status;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private CandidateScheduleEntity candidateSchedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CandidateScheduleMemberStatus getStatus() {
        return status;
    }

    public void setStatus(CandidateScheduleMemberStatus status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CandidateScheduleEntity getCandidateSchedule() {
        return candidateSchedule;
    }

    public void setCandidateSchedule(CandidateScheduleEntity candidateSchedule) {
        this.candidateSchedule = candidateSchedule;
    }

    @Override
    public String toString() {
        return "CandidateScheduleMemberEntity{" +
            "id=" + id +
            ", status=" + status +
            '}';
    }
}
