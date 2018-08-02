package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PaymentTransactionEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {

    Optional<PaymentTransactionEntity> findById(Long id);
}
