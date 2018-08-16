package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateScheduleEntity;
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

    List<CandidateScheduleEntity> findAllByCandidate_Id(Long candidateId);

    List<CandidateScheduleEntity> findAllByOwner(Long ownerId);

    List<CandidateScheduleEntity>
    findAllByOwnerAndScheduleDateAfterAndScheduleDateBefore(Long ownerId, ZonedDateTime startDate, ZonedDateTime endDate);

}
