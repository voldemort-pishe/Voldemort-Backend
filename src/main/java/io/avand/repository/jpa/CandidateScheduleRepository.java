package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the CandidateScheduleEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateScheduleRepository extends JpaRepository<CandidateScheduleEntity, Long> {

    Page<CandidateScheduleEntity> findAllByCandidate_IdAndCandidate_Job_Company_Id(
        Long candidateId,
        Long companyId,
        Pageable pageable
    );

    Page<CandidateScheduleEntity> findAllByCandidate_Job_Company_Id(Long companyId, Pageable pageable);

    Page<CandidateScheduleEntity>
    findAllByCandidate_Job_Company_IdAndStartDateBeforeAndEndDateAfter(
        Long companyId,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        Pageable pageable
    );

}
