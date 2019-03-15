package hr.pishe.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CompanyDTO extends AbstractAuditingDTO implements Serializable {

    private String nameEn;

    private String nameFa;

    private String descriptionEn;

    private String descriptionFa;

    private String language;

    private String subDomain;

    private Long fileId;
    @NotNull
    private CompanyContactDTO contact;

    @JsonIgnore
    private Set<JobDTO> jobs = new HashSet<>();

    @JsonIgnore
    private Set<EvaluationCriteriaDTO> evaluationCriteria = new HashSet<>();

    @JsonIgnore
    private Set<CompanyPipelineDTO> companyPipelines = new HashSet<>();

    @JsonIgnore
    private Set<InvoiceDTO> invoices = new HashSet<>();

    private Long userId;

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameFa() {
        return nameFa;
    }

    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionFa() {
        return descriptionFa;
    }

    public void setDescriptionFa(String descriptionFa) {
        this.descriptionFa = descriptionFa;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public CompanyContactDTO getContact() {
        return contact;
    }

    public void setContact(CompanyContactDTO contact) {
        this.contact = contact;
    }

    public Set<JobDTO> getJobs() {
        return jobs;
    }

    public void setJobs(Set<JobDTO> jobs) {
        this.jobs = jobs;
    }

    public Set<EvaluationCriteriaDTO> getEvaluationCriteria() {
        return evaluationCriteria;
    }

    public void setEvaluationCriteria(Set<EvaluationCriteriaDTO> evaluationCriteria) {
        this.evaluationCriteria = evaluationCriteria;
    }

    public Set<CompanyPipelineDTO> getCompanyPipelines() {
        return companyPipelines;
    }

    public void setCompanyPipelines(Set<CompanyPipelineDTO> companyPipelines) {
        this.companyPipelines = companyPipelines;
    }

    public Set<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "nameEn='" + nameEn + '\'' +
            ", nameFa='" + nameFa + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", descriptionFa='" + descriptionFa + '\'' +
            ", language='" + language + '\'' +
            ", subDomain='" + subDomain + '\'' +
            ", fileId=" + fileId +
            ", contact=" + contact +
            ", userId=" + userId +
            '}';
    }
}
