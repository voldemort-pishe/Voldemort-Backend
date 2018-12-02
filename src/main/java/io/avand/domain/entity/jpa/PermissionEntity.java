package io.avand.domain.entity.jpa;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.domain.enumeration.PermissionType;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne
    private AuthorityEntity authority;

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

    public AuthorityEntity getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityEntity authority) {
        this.authority = authority;
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
