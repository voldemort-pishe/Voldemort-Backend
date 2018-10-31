package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateMessageRepository extends JpaRepository<CandidateMessageEntity, Long> {

    Page<CandidateMessageEntity> findAllByCandidate_Id(Long candidateId, Pageable pageable);

    Optional<CandidateMessageEntity> findByMessageId(String messageId);

}
