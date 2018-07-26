package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CompanyEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    Page<CompanyEntity> findAllByUser_Id(Long userId, Pageable pageable);
}
