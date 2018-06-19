package io.avand.repository;

import io.avand.domain.UserEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

}
