package io.avand.repository;

import io.avand.domain.CandidateEvaluationCriteriaEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateEvaluationCriteriaEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateEvaluationCriteriaEntityRepository extends JpaRepository<CandidateEvaluationCriteriaEntity, Long> {

}
