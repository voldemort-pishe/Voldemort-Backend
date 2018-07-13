package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CompanyMemberEntity;
import io.avand.service.dto.CompanyMemberDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompanyMemberMapper extends EntityMapper<CompanyMemberDTO, CompanyMemberEntity> {
}
