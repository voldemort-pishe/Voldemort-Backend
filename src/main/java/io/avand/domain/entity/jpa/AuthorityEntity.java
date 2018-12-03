package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A AuthorityEntity.
 */
@Entity
@Table(name = "authority_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "authority_permission_entity",
        joinColumns = {@JoinColumn(name = "authority_id")},
        inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    @JsonIgnore
    private Set<PermissionEntity> permissions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AuthorityEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorityEntity authorityEntity = (AuthorityEntity) o;
        if (authorityEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), authorityEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuthorityEntity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
