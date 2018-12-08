package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CandidateScheduleMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateScheduleMemberRepository extends JpaRepository<CandidateScheduleMemberEntity, Long> {

    List<CandidateScheduleMemberEntity> findAllByCandidateSchedule_Id(Long id);
}
