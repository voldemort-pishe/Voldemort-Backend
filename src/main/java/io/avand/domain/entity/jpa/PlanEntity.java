package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.PlanType;
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
@Table(name = "plan_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class PlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "length")
    private Integer length;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PlanType type;

    @OneToMany(mappedBy = "plan")
    @JsonIgnore
    private Set<PlanConfigEntity> planConfig = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public PlanEntity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getAmount() {
        return amount;
    }

    public PlanEntity amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActivation(Boolean activation) {
        this.active = activation;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public Set<PlanConfigEntity> getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(Set<PlanConfigEntity> planConfig) {
        this.planConfig = planConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlanEntity planEntity = (PlanEntity) o;
        if (planEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planEntity.getId());
    }

    @Override
    public String toString() {
        return "PlanEntity{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", amount=" + amount +
            ", length=" + length +
            ", active=" + active +
            ", type=" + type +
            '}';
    }
}
