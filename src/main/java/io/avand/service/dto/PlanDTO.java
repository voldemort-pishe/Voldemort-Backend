package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.PlanType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PlanDTO implements Serializable {

    @NotNull
    private Long id;

    private String title;

    private String description;

    private Long amount;

    private Long length;

    private Long extraLength;

    private Boolean active;

    private PlanType type;

    @JsonIgnore
    private Set<PlanConfigDTO> planConfig = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getExtraLength() {
        return extraLength;
    }

    public void setExtraLength(Long extraLength) {
        this.extraLength = extraLength;
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

    public Set<PlanConfigDTO> getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(Set<PlanConfigDTO> planConfig) {
        this.planConfig = planConfig;
    }

    @Override
    public String toString() {
        return "PlanDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", amount=" + amount +
            ", length=" + length +
            ", extraLength=" + extraLength +
            ", active=" + active +
            ", type=" + type +
            '}';
    }
}
