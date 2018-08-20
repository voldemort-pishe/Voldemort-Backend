package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PlanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<PlanEntity> findAllByActiveIsTrue(Pageable pageable);
}
