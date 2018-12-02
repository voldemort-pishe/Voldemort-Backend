package io.avand.domain.entity.jpa;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.domain.enumeration.PermissionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission_entity")
public class PermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PermissionType type;

    @Column(name = "access")
    @Enumerated(EnumType.STRING)
    private PermissionAccess access;

    @ManyToMany(mappedBy = "permissions")
    private Set<AuthorityEntity> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }

    public PermissionAccess getAccess() {
        return access;
    }

    public void setAccess(PermissionAccess access) {
        this.access = access;
    }

    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "PermissionEntity{" +
            "id=" + id +
            ", type=" + type +
            ", access=" + access +
            '}';
    }
}
