package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CandidateSocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateSocialRepository extends JpaRepository<CandidateSocialEntity, Long> {
    List<CandidateSocialEntity> findAllByCandidate_Id(Long candidateId);
}
