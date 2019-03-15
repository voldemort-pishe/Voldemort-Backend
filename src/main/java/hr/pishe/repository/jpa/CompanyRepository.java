package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the CompanyEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    Optional<CompanyEntity> findById(Long companyId);

    Page<CompanyEntity> findAllByUser_Id(Long userId, Pageable pageable);

    List<CompanyEntity> findAllByUser_Id(Long userId);

    CompanyEntity findBySubDomain(String subDomain);
}
