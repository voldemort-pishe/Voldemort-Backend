package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompanyEntity.
 */
@Entity
@Table(name = "company_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_fa")
    private String nameFa;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_fa")
    private String descriptionFa;

    @Column(name = "sub_domain")
    private String subDomain;

    @Column(name = "language")
    private String language;

    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private FileEntity file;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobEntity> jobs = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EvaluationCriteriaEntity> evaluationCriteria = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyPipelineEntity> companyPipelines = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyMemberEntity> companyMembers = new HashSet<>();

    @ManyToOne
    private UserEntity user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public Set<JobEntity> getJobs() {
        return jobs;
    }

    public CompanyEntity jobs(Set<JobEntity> jobEntities) {
        this.jobs = jobEntities;
        return this;
    }

    public CompanyEntity addJob(JobEntity jobEntity) {
        this.jobs.add(jobEntity);
        jobEntity.setCompany(this);
        return this;
    }

    public CompanyEntity removeJob(JobEntity jobEntity) {
        this.jobs.remove(jobEntity);
        jobEntity.setCompany(null);
        return this;
    }

    public void setJobs(Set<JobEntity> jobEntities) {
        this.jobs = jobEntities;
    }

    public Set<EvaluationCriteriaEntity> getEvaluationCriteria() {
        return evaluationCriteria;
    }

    public CompanyEntity evaluationCriteria(Set<EvaluationCriteriaEntity> evaluationCriteriaEntities) {
        this.evaluationCriteria = evaluationCriteriaEntities;
        return this;
    }

    public CompanyEntity addEvaluationCriteria(EvaluationCriteriaEntity evaluationCriteriaEntity) {
        this.evaluationCriteria.add(evaluationCriteriaEntity);
        evaluationCriteriaEntity.setCompany(this);
        return this;
    }

    public CompanyEntity removeEvaluationCriteria(EvaluationCriteriaEntity evaluationCriteriaEntity) {
        this.evaluationCriteria.remove(evaluationCriteriaEntity);
        evaluationCriteriaEntity.setCompany(null);
        return this;
    }

    public void setEvaluationCriteria(Set<EvaluationCriteriaEntity> evaluationCriteriaEntities) {
        this.evaluationCriteria = evaluationCriteriaEntities;
    }

    public Set<CompanyPipelineEntity> getCompanyPipelines() {
        return companyPipelines;
    }

    public CompanyEntity companyPipelines(Set<CompanyPipelineEntity> companyPipelineEntities) {
        this.companyPipelines = companyPipelineEntities;
        return this;
    }

    public CompanyEntity addCompanyPipeline(CompanyPipelineEntity companyPipelineEntity) {
        this.companyPipelines.add(companyPipelineEntity);
        companyPipelineEntity.setCompany(this);
        return this;
    }

    public CompanyEntity removeCompanyPipeline(CompanyPipelineEntity companyPipelineEntity) {
        this.companyPipelines.remove(companyPipelineEntity);
        companyPipelineEntity.setCompany(null);
        return this;
    }

    public void setCompanyPipelines(Set<CompanyPipelineEntity> companyPipelineEntities) {
        this.companyPipelines = companyPipelineEntities;
    }

    public Set<CompanyMemberEntity> getCompanyMembers() {
        return companyMembers;
    }

    public void setCompanyMembers(Set<CompanyMemberEntity> companyMemberEntities) {
        this.companyMembers = companyMemberEntities;
    }

    public UserEntity getUser() {
        return user;
    }

    public CompanyEntity user(UserEntity userEntity) {
        this.user = userEntity;
        return this;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyEntity companyEntity = (CompanyEntity) o;
        if (companyEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
            "id=" + id +
            ", nameEn='" + nameEn + '\'' +
            ", nameFa='" + nameFa + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", descriptionFa='" + descriptionFa + '\'' +
            ", subDomain='" + subDomain + '\'' +
            ", language='" + language + '\'' +
            ", file=" + file +
            ", jobs=" + jobs +
            ", evaluationCriteria=" + evaluationCriteria +
            ", companyPipelines=" + companyPipelines +
            ", companyMembers=" + companyMembers +
            '}';
    }
}
