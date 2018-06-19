package io.avand.repository;

import io.avand.domain.CandidateScheduleEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateScheduleEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateScheduleEntityRepository extends JpaRepository<CandidateScheduleEntity, Long> {

}
