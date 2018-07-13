package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.JobType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A JobEntity.
 */
@Entity
@Table(name = "job_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private JobType type;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CandidateEntity> candidate = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private CompanyEntity company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public JobEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }


    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<CandidateEntity> getCandidate() {
        return candidate;
    }

    public void setCandidate(Set<CandidateEntity> candidate) {
        this.candidate = candidate;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public JobEntity company(CompanyEntity companyEntity) {
        this.company = companyEntity;
        return this;
    }

    public void setCompany(CompanyEntity companyEntity) {
        this.company = companyEntity;
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
        JobEntity jobEntity = (JobEntity) o;
        if (jobEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobEntity{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type=" + type +
            ", location='" + location + '\'' +
            '}';
    }
}
