package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyMemberEntity;
import io.avand.service.dto.CompanyMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompanyMemberMapper extends EntityMapper<CompanyMemberDTO, CompanyMemberEntity> {

    @Override
    @Mapping(source = "company.id",target = "companyId")
    @Mapping(source = "user.id",target = "userId")
    CompanyMemberDTO toDto(CompanyMemberEntity entity);
}
