package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.CompanyMemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMemberRepository extends JpaRepository<CompanyMemberEntity, Long> {

    Page<CompanyMemberEntity> findAllByCompany_User_IdAndCompany_Id(Long userId, Long companyId, Pageable pageable);
}
