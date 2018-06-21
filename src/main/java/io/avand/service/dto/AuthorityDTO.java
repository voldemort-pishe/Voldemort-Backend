package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AuthorityDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorityDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
