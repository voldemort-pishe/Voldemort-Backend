package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.UserAuthorityEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserAuthorityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthorityEntity, Long> {

}
