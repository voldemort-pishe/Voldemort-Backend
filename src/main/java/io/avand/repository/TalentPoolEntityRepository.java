package io.avand.repository;

import io.avand.domain.TalentPoolEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TalentPoolEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalentPoolEntityRepository extends JpaRepository<TalentPoolEntity, Long> {

}
