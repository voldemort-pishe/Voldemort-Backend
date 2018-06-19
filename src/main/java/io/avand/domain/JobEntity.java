package io.avand.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

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

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private CandidateEntity candidate;

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

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public JobEntity candidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
        return this;
    }

    public void setCandidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
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
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
