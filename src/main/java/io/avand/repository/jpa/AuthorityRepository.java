package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.AuthorityEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuthorityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {

    AuthorityEntity findByName(String name);

}
