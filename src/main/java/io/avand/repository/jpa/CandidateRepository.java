package io.avand.repository.jpa;

import io.avand.domain.CandidateEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

}
