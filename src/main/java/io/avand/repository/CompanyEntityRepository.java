package io.avand.repository;

import io.avand.domain.CompanyEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

}
