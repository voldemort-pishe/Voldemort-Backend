package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

    Page<CandidateEntity> findAllByJob_IdAndJob_Company_User_Id(Long jobId,Long userId, Pageable pageable);

}
