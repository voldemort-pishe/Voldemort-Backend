package io.avand.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.avand.domain.enumeration.PermissionAction;

/**
 * A UserPermissionEntity.
 */
@Entity
@Table(name = "user_permission_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private PermissionAction action;

    @ManyToOne
    private UserAuthorityEntity userAuthority;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionAction getAction() {
        return action;
    }

    public UserPermissionEntity action(PermissionAction action) {
        this.action = action;
        return this;
    }

    public void setAction(PermissionAction action) {
        this.action = action;
    }

    public UserAuthorityEntity getUserAuthority() {
        return userAuthority;
    }

    public UserPermissionEntity userAuthority(UserAuthorityEntity userAuthorityEntity) {
        this.userAuthority = userAuthorityEntity;
        return this;
    }

    public void setUserAuthority(UserAuthorityEntity userAuthorityEntity) {
        this.userAuthority = userAuthorityEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPermissionEntity userPermissionEntity = (UserPermissionEntity) o;
        if (userPermissionEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPermissionEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPermissionEntity{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            "}";
    }
}
