package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the InvoiceEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    Page<InvoiceEntity> findAllByCompany_Id(Long id, Pageable pageable);

    Optional<InvoiceEntity> findByIdAndCompany_Id(Long id, Long companyId);

    Optional<InvoiceEntity> findByTrackingCode(String trackingCode);
}
