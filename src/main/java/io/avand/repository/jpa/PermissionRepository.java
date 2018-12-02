package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PermissionEntity;
import io.avand.domain.enumeration.PermissionAccess;
import io.avand.domain.enumeration.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    List<PermissionEntity> findAllByAuthority_Name(String authorityName);

    Boolean existsByAccessAndTypeAndAuthority_NameIn(PermissionAccess access,
                                                     PermissionType type,
                                                     List<String> names);
}
