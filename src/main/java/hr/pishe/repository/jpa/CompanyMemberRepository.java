package hr.pishe.repository.jpa;

import hr.pishe.domain.entity.jpa.CompanyMemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyMemberRepository extends JpaRepository<CompanyMemberEntity, Long>,
    JpaSpecificationExecutor<CompanyMemberEntity> {

    CompanyMemberEntity findByIdAndCompany_Id(Long id, Long companyId);

    CompanyMemberEntity findByUser_Id(Long userId);

    Optional<CompanyMemberEntity> findByUser_Login(String login);

    Page<CompanyMemberEntity> findAllByCompany_Id(Long companyId, Pageable pageable);

    Page<CompanyMemberEntity> findAllByUser_ActivatedAndCompany_Id(Boolean activated, Long companyId, Pageable pageable);
}
