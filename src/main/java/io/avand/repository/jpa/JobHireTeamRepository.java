package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.JobHireTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobHireTeamRepository extends JpaRepository<JobHireTeamEntity, Long> {

    List<JobHireTeamEntity> findByJob_IdAndJob_Company_Id(Long jobId, Long companyId);

    List<JobHireTeamEntity> findAllByUser_LoginAndJob_Id(String login,Long jobId);
}
