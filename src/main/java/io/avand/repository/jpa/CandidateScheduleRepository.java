package io.avand.repository.jpa;

import io.avand.domain.CandidateScheduleEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateScheduleEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateScheduleRepository extends JpaRepository<CandidateScheduleEntity, Long> {

}
