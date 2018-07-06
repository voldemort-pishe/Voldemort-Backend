package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AuthorityDTO extends AbstractAuditingDTO implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorityDTO{" +
            "name='" + name + '\'' +
            '}';
    }
}
