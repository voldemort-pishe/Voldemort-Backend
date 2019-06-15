package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CompanyContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyContactRepository extends JpaRepository<CompanyContactEntity, Long> {
}
