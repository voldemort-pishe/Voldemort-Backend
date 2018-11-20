package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommentEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    CommentEntity findByIdAndCandidate_Job_Company_Id(Long id,Long companyId);

    Page<CommentEntity> findAllByCandidate_IdAndCandidate_Job_Company_Id(Pageable pageable, Long id, Long companyId);

}
