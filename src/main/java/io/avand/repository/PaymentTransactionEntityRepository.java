package io.avand.repository;

import io.avand.domain.PaymentTransactionEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaymentTransactionEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTransactionEntityRepository extends JpaRepository<PaymentTransactionEntity, Long> {

}
