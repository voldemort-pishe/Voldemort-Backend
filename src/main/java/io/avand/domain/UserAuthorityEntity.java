package io.avand.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserAuthorityEntity.
 */
@Entity
@Table(name = "user_authority_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAuthorityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority_name")
    private String authorityName;

    @OneToMany(mappedBy = "userAuthority")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserPermissionEntity> userPermissions = new HashSet<>();

    @ManyToOne
    private UserEntity user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public UserAuthorityEntity authorityName(String authorityName) {
        this.authorityName = authorityName;
        return this;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Set<UserPermissionEntity> getUserPermissions() {
        return userPermissions;
    }

    public UserAuthorityEntity userPermissions(Set<UserPermissionEntity> userPermissionEntities) {
        this.userPermissions = userPermissionEntities;
        return this;
    }

    public UserAuthorityEntity addUserPermission(UserPermissionEntity userPermissionEntity) {
        this.userPermissions.add(userPermissionEntity);
        userPermissionEntity.setUserAuthority(this);
        return this;
    }

    public UserAuthorityEntity removeUserPermission(UserPermissionEntity userPermissionEntity) {
        this.userPermissions.remove(userPermissionEntity);
        userPermissionEntity.setUserAuthority(null);
        return this;
    }

    public void setUserPermissions(Set<UserPermissionEntity> userPermissionEntities) {
        this.userPermissions = userPermissionEntities;
    }

    public UserEntity getUser() {
        return user;
    }

    public UserAuthorityEntity user(UserEntity userEntity) {
        this.user = userEntity;
        return this;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
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
        UserAuthorityEntity userAuthorityEntity = (UserAuthorityEntity) o;
        if (userAuthorityEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAuthorityEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAuthorityEntity{" +
            "id=" + getId() +
            ", authorityName='" + getAuthorityName() + "'" +
            "}";
    }
}
