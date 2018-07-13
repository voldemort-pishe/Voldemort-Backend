package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.JobEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the JobEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    List<JobEntity> findAllByCompany_User_Id(Long id);

}
