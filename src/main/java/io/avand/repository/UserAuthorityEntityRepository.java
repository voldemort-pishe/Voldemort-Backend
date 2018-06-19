package io.avand.repository;

import io.avand.domain.UserAuthorityEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserAuthorityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAuthorityEntityRepository extends JpaRepository<UserAuthorityEntity, Long> {

}
