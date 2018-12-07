package io.avand.service.mapper;

import io.avand.domain.entity.jpa.UserEntity;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.vm.response.UserIncludeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserAuthorityMapper.class, CompanyMapper.class, InvoiceMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, UserEntity>, VmMapper<UserDTO, UserIncludeVM> {

    @Override
    @Mapping(source = "file.id",target = "fileId")
    UserDTO toDto(UserEntity entity);
}
