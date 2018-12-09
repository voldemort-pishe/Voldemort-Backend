package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.enumeration.InvoiceStatus;
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
