package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PlanDTO implements Serializable {

    @NotNull
    private Long id;

    private String title;

    private String description;

    private Integer amount;

    private Integer length;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

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

    @Override
    public String toString() {
        return "PlanDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", amount=" + amount +
            ", length=" + length +
            ", active=" + active +
            '}';
    }
}
