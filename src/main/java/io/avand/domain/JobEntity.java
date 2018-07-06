package io.avand.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
