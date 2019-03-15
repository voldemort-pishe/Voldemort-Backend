package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CandidateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the CandidateEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long>,
    JpaSpecificationExecutor<CandidateEntity> {

    CandidateEntity findByIdAndJob_Company_Id(Long id, Long companyId);

    Page<CandidateEntity> findAllByJob_IdAndJob_Company_User_Id(Long jobId, Long userId, Pageable pageable);

    Page<CandidateEntity> findAllByJob_Company_IdAndJob_Company_User_Id(Long companyId, Long userId, Pageable pageable);

}
