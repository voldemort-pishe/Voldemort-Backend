package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.PlanEntity;
import hr.pishe.domain.enumeration.PlanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PlanEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

    Optional<PlanEntity> findById(Long planId);

    Optional<PlanEntity> findFirstByType(PlanType type);

    Page<PlanEntity> findAllByActiveIsTrueAndType(Pageable pageable, PlanType type);

    Page<PlanEntity> findAllByType(Pageable pageable, PlanType type);
}
