package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.PermissionEntity;
import hr.pishe.domain.enumeration.PermissionAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    PermissionEntity findByAccess(PermissionAccess access);

}
