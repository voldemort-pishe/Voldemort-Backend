package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateMessageRepository extends JpaRepository<CandidateMessageEntity, Long> {

    CandidateMessageEntity findByIdAndCandidate_Job_Company_Id(Long id, Long companyId);

    Page<CandidateMessageEntity> findAllByCandidate_IdAndCandidate_Job_Company_Id(Long candidateId, Long companyId, Pageable pageable);

    Optional<CandidateMessageEntity> findByMessageId(String messageId);

}
