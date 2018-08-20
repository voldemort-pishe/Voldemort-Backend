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
@Table(name = "user_plan_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class UserPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "length")
    private Integer length;

    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private Set<UserPlanConfigEntity> planConfig = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private UserEntity user;

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

    public Set<UserPlanConfigEntity> getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(Set<UserPlanConfigEntity> planConfig) {
        this.planConfig = planConfig;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
        UserPlanEntity planEntity = (UserPlanEntity) o;
        if (planEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planEntity.getId());
    }

    @Override
    public String toString() {
        return "UserPlanEntity{" +
            "id=" + id +
            ", length=" + length +
            '}';
    }
}
