package io.avand.repository.jpa;

import io.avand.domain.CompanyEntity;
import io.avand.domain.UserEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CompanyEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    List<CompanyEntity> findAllByUser_Id(Long userId);
}
