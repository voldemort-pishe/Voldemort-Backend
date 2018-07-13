package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CommentEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommentEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
