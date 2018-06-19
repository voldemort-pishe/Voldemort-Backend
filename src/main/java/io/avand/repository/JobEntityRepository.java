package io.avand.repository;

import io.avand.domain.JobEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

}
