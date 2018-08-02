package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.SubscriptionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistoryEntity, Long> {
}
