package io.avand.repository.jpa;

import io.avand.domain.EvaluationCriteriaEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EvaluationCriteriaEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteriaEntity, Long> {

}
