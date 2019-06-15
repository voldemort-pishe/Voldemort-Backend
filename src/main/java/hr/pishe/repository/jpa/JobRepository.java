package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.JobEntity;
import hr.pishe.domain.enumeration.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the JobEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long>, JpaSpecificationExecutor<JobEntity> {

    JobEntity findByIdAndCompany_Id(Long id, Long companyId);

    Page<JobEntity> findAllByCompany_Id(Pageable pageable, Long id);

    JobEntity findByUniqueIdAndCompany_SubDomain(String uniqueId, String subDomain);

    List<JobEntity> findAllByCompany_SubDomainAndStatus(String subDomain, JobStatus status);

}
