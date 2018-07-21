package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateMessageRepository extends JpaRepository<CandidateMessageEntity, Long> {

    List<CandidateMessageEntity> findAllByCandidate_Id(Long candidateId);

}
