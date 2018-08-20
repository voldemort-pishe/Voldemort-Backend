package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserPlanDTO implements Serializable {

    @NotNull
    private Long id;

    private Integer length;

    @JsonIgnore
    private Set<UserPlanConfigDTO> planConfig = new HashSet<>();

    @NotNull
    private Long userId;

    @NotNull
    private Long invoiceId;

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

    public Set<UserPlanConfigDTO> getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(Set<UserPlanConfigDTO> planConfig) {
        this.planConfig = planConfig;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "UserPlanDTO{" +
            "id=" + id +
            ", length=" + length +
            ", userId=" + userId +
            ", invoiceId=" + invoiceId +
            '}';
    }
}
