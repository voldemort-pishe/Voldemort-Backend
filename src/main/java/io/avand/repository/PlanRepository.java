package io.avand.repository;

import io.avand.domain.PlanEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlanEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

}
