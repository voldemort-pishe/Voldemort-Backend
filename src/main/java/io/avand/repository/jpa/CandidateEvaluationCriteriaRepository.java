package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateEvaluationCriteriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the CandidateEvaluationCriteriaEntity entity.
 */
@Repository
public interface CandidateEvaluationCriteriaRepository extends JpaRepository<CandidateEvaluationCriteriaEntity, Long> {

    Optional<CandidateEvaluationCriteriaEntity> findByIdAndCandidate_Job_Company_Id(Long id, Long companyId);

    Boolean existsByUserIdAndCandidate_Id(Long userId, Long candidateId);

    Page<CandidateEvaluationCriteriaEntity> findAllByCandidate_IdAndCandidate_Job_Company_Id(Long id, Long companyId, Pageable pageable);

}
