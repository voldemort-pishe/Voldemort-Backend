package io.avand.repository;

import io.avand.domain.CompanyPipelineEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyPipelineEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyPipelineEntityRepository extends JpaRepository<CompanyPipelineEntity, Long> {

}
