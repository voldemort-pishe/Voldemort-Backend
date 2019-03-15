package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.UserStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStateRepository extends JpaRepository<UserStateEntity, Long> {
    UserStateEntity findByUserId(Long userId);
}
