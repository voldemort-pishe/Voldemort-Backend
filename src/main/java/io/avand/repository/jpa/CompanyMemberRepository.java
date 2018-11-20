package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CompanyMemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMemberRepository extends JpaRepository<CompanyMemberEntity, Long> {

    CompanyMemberEntity findByIdAndCompany_Id(Long id,Long companyId);

    CompanyMemberEntity findByUser_Id(Long userId);

    Page<CompanyMemberEntity> findAllByCompany_Id(Long companyId, Pageable pageable);

    Page<CompanyMemberEntity> findAllByUser_ActivatedAndCompany_Id(Boolean activated,Long companyId, Pageable pageable);
}
