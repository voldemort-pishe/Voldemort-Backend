package hr.pishe.service.dto;

import hr.pishe.domain.enumeration.UserStateType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserStateDTO implements Serializable {
    private Long id;
    private Long userId;
    @NotNull
    private UserStateType state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserStateType getState() {
        return state;
    }

    public void setState(UserStateType state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserStateDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", state=" + state +
            '}';
    }
}
