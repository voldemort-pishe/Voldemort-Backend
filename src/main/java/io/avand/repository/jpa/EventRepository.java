package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    EventEntity findByIdAndOwner_Id(Long id, Long ownerId);

    List<EventEntity> findAllByOwner_Id(Long ownerId);

    List<EventEntity> findAllByOwner_IdAndCreatedDateBetween(Long ownerId, ZonedDateTime startDate, ZonedDateTime endDate);
}
