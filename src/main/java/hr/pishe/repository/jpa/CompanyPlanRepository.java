package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CompanyPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PlanEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyPlanRepository extends JpaRepository<CompanyPlanEntity, Long> {

    Optional<CompanyPlanEntity> findById(Long planId);

    Optional<CompanyPlanEntity> findByCompany_Id(Long companyId);

    Optional<CompanyPlanEntity> findByInvoice_Id(Long invoiceId);

}
