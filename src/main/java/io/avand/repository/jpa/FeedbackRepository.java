package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the FeedbackEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {

    Optional<FeedbackEntity> findByUserIdAndCandidate_Id(Long userId,Long candidateId);

    FeedbackEntity findByIdAndCandidate_Job_Company_Id(Long id,Long companyId);

    Page<FeedbackEntity> findAllByCandidate_Job_Company_Id(Long companyId,Pageable pageable);

    Page<FeedbackEntity> findAllByCandidate_IdAndCandidate_Job_Company_Id(Long id,Long companyId,Pageable pageable);

}
