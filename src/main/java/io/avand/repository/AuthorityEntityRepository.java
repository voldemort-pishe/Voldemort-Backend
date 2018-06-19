package io.avand.repository;

import io.avand.domain.AuthorityEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuthorityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorityEntityRepository extends JpaRepository<AuthorityEntity, Long> {

}
