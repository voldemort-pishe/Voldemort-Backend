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

    Page<CandidateScheduleEntity> findAllByCandidate_Id(Long candidateId,Pageable pageable);

    Page<CandidateScheduleEntity> findAllByOwner(Long ownerId, Pageable pageable);

    Page<CandidateScheduleEntity>
    findAllByOwnerAndScheduleDateAfterAndScheduleDateBefore(Long ownerId, ZonedDateTime startDate, ZonedDateTime endDate,Pageable pageable);

}
