package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CompanyPipelineEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyPipelineEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyPipelineRepository extends JpaRepository<CompanyPipelineEntity, Long> {

}
