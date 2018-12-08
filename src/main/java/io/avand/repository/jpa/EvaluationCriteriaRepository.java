package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.EvaluationCriteriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the EvaluationCriteriaEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteriaEntity, Long> {
    Optional<EvaluationCriteriaEntity> findByIdAndCompany_Id(Long id, Long companyId);

    Page<EvaluationCriteriaEntity> findAllByCompany_Id(Long companyId, Pageable pageable);
}
