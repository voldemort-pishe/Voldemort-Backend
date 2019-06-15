package hr.pishe.web.rest.vm;

import hr.pishe.config.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UserResetPasswordInitVM implements Serializable {

    @Pattern(regexp = Constants.EMAIL_REGEX)
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
        return "UserResetPasswordInitVM{" +
            "email='" + email + '\'' +
            '}';
    }
}
