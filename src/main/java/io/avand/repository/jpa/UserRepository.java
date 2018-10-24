package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.UserEntity;
import org.springframework.cache.annotation.Cacheable;
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

    Optional<UserEntity> findByInvitationKey(String invitationKey);

    Optional<UserEntity> findByResetKey(String resetKey);

    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByEmail(String email);

}
