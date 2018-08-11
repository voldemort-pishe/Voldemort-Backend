package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.CompanyPipelineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CompanyPipelineEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyPipelineRepository extends JpaRepository<CompanyPipelineEntity, Long> {

    Page<CompanyPipelineEntity> findAllByCompany(CompanyEntity company, Pageable pageable);

    List<CompanyPipelineEntity> findAllByCompany(CompanyEntity company);
}
