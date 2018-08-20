package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.PlanConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanConfigRepository extends JpaRepository<PlanConfigEntity, Long> {
}
