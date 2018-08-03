package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.domain.entity.jpa.PaymentTransactionEntity;
import io.avand.domain.entity.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the InvoiceEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    Optional<InvoiceEntity> findById(Long id);

    Optional<InvoiceEntity> findTopByUser(UserEntity user);

    Optional<InvoiceEntity> findByPaymentTransactions(PaymentTransactionEntity paymentTransaction);
}
