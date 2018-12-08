package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PlanEntity.
 */
@Entity
@Table(name = "company_plan")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CompanyPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "length")
    private Integer length;

    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private Set<CompanyPlanConfigEntity> planConfig = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private CompanyEntity company;

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private InvoiceEntity invoice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Set<CompanyPlanConfigEntity> getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(Set<CompanyPlanConfigEntity> planConfig) {
        this.planConfig = planConfig;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyPlanEntity planEntity = (CompanyPlanEntity) o;
        if (planEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planEntity.getId());
    }

    @Override
    public String toString() {
        return "CompanyPlanEntity{" +
            "id=" + id +
            ", length=" + length +
            '}';
    }
}
