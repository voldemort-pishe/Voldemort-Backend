package io.avand.service.mapper;

import io.avand.domain.UserEntity;
import io.avand.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses =
    {UserAuthorityMapper.class,
        CompanyMapper.class,
        TalentPoolMapper.class,
        InvoiceMapper.class}
        )
public interface UserMapper extends EntityMapper<UserDTO, UserEntity> {
}
