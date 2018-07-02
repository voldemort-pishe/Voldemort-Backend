package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PlanDTO implements Serializable {

    @NotNull
    private Long id;

    private String title;

    private String description;

    private Integer amount;

    private Boolean active;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActivation(Boolean activation) {
        this.active = activation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PlanDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", amount=" + amount +
            ", active=" + active +
            '}';
    }
}
