package io.avand.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserChangePasswordVM implements Serializable {

    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserChangePasswordVM{" +
            "oldPassword='" + oldPassword + '\'' +
            ", newPassword='" + newPassword + '\'' +
            '}';
    }
}
