package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.FeedbackEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FeedbackEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {

}
