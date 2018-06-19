package io.avand.repository;

import io.avand.domain.CandidateEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateEntityRepository extends JpaRepository<CandidateEntity, Long> {

}
