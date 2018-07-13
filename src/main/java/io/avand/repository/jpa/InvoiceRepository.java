package io.avand.repository.jpa;

import io.avand.domain.InvoiceEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InvoiceEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

}
