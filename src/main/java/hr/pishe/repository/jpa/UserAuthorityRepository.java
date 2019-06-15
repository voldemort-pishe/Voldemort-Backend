package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.UserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the UserAuthorityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthorityEntity, Long> {

    List<UserAuthorityEntity> findAllByUser_Id(Long userId);

    Optional<UserAuthorityEntity> findByAuthority_NameAndUser_Id(String authorityName, Long userId);

}
