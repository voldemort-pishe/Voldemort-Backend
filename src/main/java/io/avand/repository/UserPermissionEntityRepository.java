package io.avand.repository;

import io.avand.domain.UserPermissionEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserPermissionEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPermissionEntityRepository extends JpaRepository<UserPermissionEntity, Long> {

}
