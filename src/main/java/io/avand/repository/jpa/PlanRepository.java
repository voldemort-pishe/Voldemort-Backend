package io.avand.repository.jpa;

import io.avand.domain.PlanEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the PlanEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

    Optional<PlanEntity> findById(Long planId);

    Optional<PlanEntity> findByTitle(String planTitle);

    List<PlanEntity> findAllByActiveIsTrue();
}
