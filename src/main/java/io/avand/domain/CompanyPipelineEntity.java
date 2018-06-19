package io.avand.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyPipelineEntity.
 */
@Entity
@Table(name = "company_pipeline_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyPipelineEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    private CompanyEntity company;

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

    public CompanyPipelineEntity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWeight() {
        return weight;
    }

    public CompanyPipelineEntity weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public CompanyPipelineEntity company(CompanyEntity companyEntity) {
        this.company = companyEntity;
        return this;
    }

    public void setCompany(CompanyEntity companyEntity) {
        this.company = companyEntity;
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
        CompanyPipelineEntity companyPipelineEntity = (CompanyPipelineEntity) o;
        if (companyPipelineEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyPipelineEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyPipelineEntity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
