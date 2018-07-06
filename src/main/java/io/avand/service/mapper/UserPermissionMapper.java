package io.avand.service.mapper;

import io.avand.domain.UserAuthorityEntity;
import io.avand.domain.UserPermissionEntity;
import io.avand.service.dto.UserAuthorityDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.dto.UserPermissionDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {})
public interface UserPermissionMapper extends EntityMapper<UserPermissionDTO, UserPermissionEntity> {
}
