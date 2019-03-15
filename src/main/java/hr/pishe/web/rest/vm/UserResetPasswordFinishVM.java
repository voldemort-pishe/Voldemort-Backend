package hr.pishe.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserResetPasswordFinishVM implements Serializable {

    @NotNull
    private String key;
    @NotNull
    private String password;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserResetPasswordFinishVM{" +
            "key='" + key + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
