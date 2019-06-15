package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CandidateScheduleMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateScheduleMemberRepository extends JpaRepository<CandidateScheduleMemberEntity, Long> {

    List<CandidateScheduleMemberEntity> findAllByUser_Id(Long userId);

    List<CandidateScheduleMemberEntity> findAllByCandidateSchedule_Id(Long id);

    CandidateScheduleMemberEntity findByCandidateSchedule_IdAndUser_Id(Long scheduleId, Long userId);
}
