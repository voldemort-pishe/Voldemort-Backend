package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@SuppressWarnings("unused")
@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    SubscriptionEntity findByUser_Id(Long userId);

    SubscriptionEntity findByStartDateBeforeAndEndDateAfterAndUser_Id(ZonedDateTime start,ZonedDateTime end,Long userId);
}
