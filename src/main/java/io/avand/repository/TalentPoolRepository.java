package io.avand.repository;

import io.avand.domain.TalentPoolEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TalentPoolEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalentPoolRepository extends JpaRepository<TalentPoolEntity, Long> {

}
