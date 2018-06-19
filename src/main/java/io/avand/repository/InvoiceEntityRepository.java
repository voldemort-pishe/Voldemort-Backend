package io.avand.repository;

import io.avand.domain.InvoiceEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InvoiceEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceEntityRepository extends JpaRepository<InvoiceEntity, Long> {

}
