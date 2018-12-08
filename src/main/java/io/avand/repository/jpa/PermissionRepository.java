package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PermissionEntity;
import io.avand.domain.enumeration.PermissionAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    PermissionEntity findByAccess(PermissionAccess access);

}
