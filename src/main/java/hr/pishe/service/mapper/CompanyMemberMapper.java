package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CompanyMemberEntity;
import hr.pishe.service.dto.CompanyMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompanyMemberMapper extends EntityMapper<CompanyMemberDTO, CompanyMemberEntity> {

    @Override
    @Mapping(source = "company.id",target = "companyId")
    @Mapping(source = "user.email",target = "userEmail")
    CompanyMemberDTO toDto(CompanyMemberEntity entity);
}
