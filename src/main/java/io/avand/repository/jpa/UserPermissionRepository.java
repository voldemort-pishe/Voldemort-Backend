package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.UserPermissionEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserPermissionEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity, Long> {

}
