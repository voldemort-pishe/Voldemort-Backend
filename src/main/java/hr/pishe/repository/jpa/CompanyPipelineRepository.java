package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CompanyPipelineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the CompanyPipelineEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyPipelineRepository extends JpaRepository<CompanyPipelineEntity, Long> {

    Page<CompanyPipelineEntity> findAllByCompany_Id(Long companyId, Pageable pageable);

    CompanyPipelineEntity findByIdAndCompany_Id(Long id,Long companyId);
}
