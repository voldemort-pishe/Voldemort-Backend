package hr.pishe.domain.entity.jpa;

import hr.pishe.domain.enumeration.JobHireTeamRole;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "job_hire_team")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class JobHireTeamEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private JobHireTeamRole role;

    @ManyToOne(optional = false)
    @NotNull
    private UserEntity user;

    @ManyToOne(optional = false)
    @NotNull
    private JobEntity job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobHireTeamRole getRole() {
        return role;
    }

    public void setRole(JobHireTeamRole role) {
        this.role = role;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "JobHireTeamEntity{" +
            "id=" + id +
            ", role=" + role +
            ", user=" + user +
            ", job=" + job +
            '}';
    }
}
