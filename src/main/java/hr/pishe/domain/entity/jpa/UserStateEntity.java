package hr.pishe.domain.entity.jpa;

import hr.pishe.domain.enumeration.UserStateType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_state")
public class UserStateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
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
        return "UserStateEntity{" +
            "id=" + id +
            ", userId=" + userId +
            ", state=" + state +
            '}';
    }
}
