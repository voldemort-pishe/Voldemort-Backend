package hr.pishe.domain.entity.jpa;

import hr.pishe.domain.enumeration.PlanConfigType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plan_config")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class PlanConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PlanConfigType type;

    @Column(name = "value")
    private Long value;

    @ManyToOne
    private PlanEntity plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanConfigType getType() {
        return type;
    }

    public void setType(PlanConfigType type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public PlanEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanEntity plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "PlanConfigEntity{" +
            "id=" + id +
            ", type=" + type +
            ", value=" + value +
            '}';
    }
}
