package io.avand.repository;

import io.avand.domain.EvaluationCriteriaEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EvaluationCriteriaEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationCriteriaEntityRepository extends JpaRepository<EvaluationCriteriaEntity, Long> {

}
