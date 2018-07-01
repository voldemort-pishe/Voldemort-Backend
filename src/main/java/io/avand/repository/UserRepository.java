package io.avand.repository;

import io.avand.domain.UserEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
 * Spring Data JPA repository for the UserEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByActivationKey(String activationKey);

}
