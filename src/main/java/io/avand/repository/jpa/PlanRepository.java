package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PlanEntity;
import io.avand.domain.enumeration.PlanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

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
