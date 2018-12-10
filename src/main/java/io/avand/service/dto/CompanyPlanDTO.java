package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CompanyPlanDTO implements Serializable {

    @NotNull
    private Long id;

    private Integer length;

    private Integer extraLength;

    @JsonIgnore
    private Set<CompanyPlanConfigDTO> planConfig = new HashSet<>();

    @NotNull
    private Long companyId;

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

    public Integer getExtraLength() {
        return extraLength;
    }

    public void setExtraLength(Integer extraLength) {
        this.extraLength = extraLength;
    }

    public Set<CompanyPlanConfigDTO> getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(Set<CompanyPlanConfigDTO> planConfig) {
        this.planConfig = planConfig;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "CompanyPlanDTO{" +
            "id=" + id +
            ", length=" + length +
            ", extraLength=" + extraLength +
            ", companyId=" + companyId +
            ", invoiceId=" + invoiceId +
            '}';
    }
}
