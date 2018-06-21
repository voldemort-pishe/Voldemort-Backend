package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CompanyPipelineDTO implements Serializable {

    @NotNull
    private Long id;

    private String title;

    private Integer weight;

    private CompanyDTO company;

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "CompanyPipelineDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", weight=" + weight +
            ", company=" + company +
            '}';
    }
}
