package io.avand.repository;

import io.avand.domain.FeedbackEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FeedbackEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackEntityRepository extends JpaRepository<FeedbackEntity, Long> {

}
