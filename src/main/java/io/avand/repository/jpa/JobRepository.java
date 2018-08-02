package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the JobEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    Page<JobEntity> findAllByCompany_User_Id(Long id, Pageable pageable);

    List<JobEntity> findAllByCompany_SubDomain(String subDomain);

}
