package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.UserEntity;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.web.rest.vm.response.UserIncludeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserAuthorityMapper.class, CompanyMapper.class, InvoiceMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, UserEntity>, VmMapper<UserDTO, UserIncludeVM> {

    @Override
    @Mapping(source = "file.id",target = "fileId")
    UserDTO toDto(UserEntity entity);
}
