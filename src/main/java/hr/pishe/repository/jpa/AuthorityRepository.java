package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the AuthorityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {

    AuthorityEntity findByName(String name);

}
