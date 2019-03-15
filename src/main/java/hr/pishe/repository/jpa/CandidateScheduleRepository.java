package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CandidateScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the CandidateScheduleEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateScheduleRepository extends JpaRepository<CandidateScheduleEntity, Long> {

    Page<CandidateScheduleEntity> findAllByCandidate_IdAndCandidate_Job_Company_Id(
        Long candidateId,
        Long companyId,
        Pageable pageable
    );

    Page<CandidateScheduleEntity>
    findAllByIdInAndStartDateAfterAndEndDateBefore(
        List<Long> ids,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        Pageable pageable
    );

    Page<CandidateScheduleEntity>
    findAllByCandidate_Job_Company_IdAndStartDateAfterAndEndDateBefore(
        Long companyId,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        Pageable pageable
    );

}
