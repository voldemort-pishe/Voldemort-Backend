package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PaymentTransactionEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaymentTransactionEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {

}
