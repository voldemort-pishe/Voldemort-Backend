package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.domain.entity.jpa.UserEntity;
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

    Page<InvoiceEntity> findAllByUser_Id(Long id, Pageable pageable);

    Optional<InvoiceEntity> findByIdAndUser_Id(Long id,Long userId);

    Optional<InvoiceEntity> findTopByUser(UserEntity user);

    Optional<InvoiceEntity> findByTrackingCode(String trackingCode);
}
