package io.avand.repository;

import io.avand.domain.AbstractAuditingEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AbstractAuditingEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbstractAuditingEntityRepository extends JpaRepository<AbstractAuditingEntity, Long> {

}
