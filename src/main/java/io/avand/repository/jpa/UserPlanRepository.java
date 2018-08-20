package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PlanEntity;
import io.avand.domain.entity.jpa.UserPlanEntity;
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
public interface UserPlanRepository extends JpaRepository<UserPlanEntity, Long> {

    Optional<UserPlanEntity> findById(Long planId);

    Optional<UserPlanEntity> findByUser_Id(Long userId);

    Optional<UserPlanEntity> findByInvoice_Id(Long invoiceId);

}
