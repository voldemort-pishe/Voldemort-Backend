package io.avand.web.rest.vm.response;

import java.io.Serializable;

public class CompanyPipelineIncludeVM implements Serializable {
    private Long id;
    private String title;

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

    @Override
    public String toString() {
        return "CompanyPipelineIncludeVM{" +
            "id=" + id +
            ", title='" + title + '\'' +
            '}';
    }
}
