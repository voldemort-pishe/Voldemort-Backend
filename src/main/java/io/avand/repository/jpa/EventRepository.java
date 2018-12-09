package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.EventEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.enumeration.EventStatus;
import io.avand.service.dto.EventTypeCountDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {

    EventEntity findByIdAndOwner_Id(Long id, Long ownerId);

    @Query("select new io.avand.service.dto.EventTypeCountDTO(count(e.id),e.type) " +
        "from EventEntity e " +
        "where e.owner =  :owner and e.status = :status " +
        "group by e.type")
    List<EventTypeCountDTO> countAllByStatus(@Param("owner") UserEntity owner, @Param("status") EventStatus status);

    List<EventEntity> findAllByOwner_IdAndCreatedDateBetween(Long ownerId, ZonedDateTime startDate, ZonedDateTime endDate);
}
