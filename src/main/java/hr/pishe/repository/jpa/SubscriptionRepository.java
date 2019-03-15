package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@SuppressWarnings("unused")
@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    SubscriptionEntity findByCompany_Id(Long companyId);

    SubscriptionEntity findByStartDateBeforeAndEndDateAfterAndCompany_Id(ZonedDateTime start,ZonedDateTime end,Long companyId);
}
