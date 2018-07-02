package io.avand.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserActivationVM implements Serializable {

    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserActivationVM{" +
            "email='" + email + '\'' +
            '}';
    }
}
