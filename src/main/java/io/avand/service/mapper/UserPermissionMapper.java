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

@Component
public class UserPermissionMapper implements EntityMapper<UserPermissionDTO, UserPermissionEntity> {

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Override
    public UserPermissionEntity toEntity(UserPermissionDTO dto) {
        if (dto == null) {
            return null;
        }

        UserPermissionEntity userPermissionEntity = new UserPermissionEntity();

        userPermissionEntity.setId(dto.getId());
        userPermissionEntity.setAction(dto.getAction());
        userPermissionEntity.setUserAuthority(userAuthorityMapper.toEntity(dto.getUserAuthority()));

        return userPermissionEntity;
    }

    protected UserPermissionEntity toEntity(UserPermissionDTO dto, UserAuthorityEntity parent) {
        if (dto == null) {
            return null;
        }

        UserPermissionEntity userPermissionEntity = new UserPermissionEntity();

        userPermissionEntity.setId(dto.getId());
        userPermissionEntity.setAction(dto.getAction());
        userPermissionEntity.setUserAuthority(parent);

        return userPermissionEntity;
    }

    @Override
    public UserPermissionDTO toDto(UserPermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();

        userPermissionDTO.setId(entity.getId());
        userPermissionDTO.setAction(entity.getAction());
        userPermissionDTO.setUserAuthority(userAuthorityMapper.toDto(entity.getUserAuthority()));

        return userPermissionDTO;
    }

    protected UserPermissionDTO toDto(UserPermissionEntity entity, UserAuthorityDTO parent) {
        if (entity == null) {
            return null;
        }

        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();

        userPermissionDTO.setId(entity.getId());
        userPermissionDTO.setAction(entity.getAction());
        userPermissionDTO.setUserAuthority(parent);

        return userPermissionDTO;
    }

    @Override
    public List<UserPermissionEntity> toEntity(List<UserPermissionDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<UserPermissionEntity> list = new ArrayList<UserPermissionEntity>(dtoList.size());
        for (UserPermissionDTO userPermissionDTO : dtoList) {
            list.add(toEntity(userPermissionDTO));
        }

        return list;
    }

    protected List<UserPermissionEntity> toEntity(List<UserPermissionDTO> dtoList, UserAuthorityEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<UserPermissionEntity> list = new ArrayList<UserPermissionEntity>(dtoList.size());
        for (UserPermissionDTO userPermissionDTO : dtoList) {
            list.add(toEntity(userPermissionDTO, parent));
        }

        return list;
    }

    @Override
    public List<UserPermissionDTO> toDto(List<UserPermissionEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<UserPermissionDTO> list = new ArrayList<UserPermissionDTO>(entityList.size());
        for (UserPermissionEntity userPermissionEntity : entityList) {
            list.add(toDto(userPermissionEntity));
        }

        return list;
    }

    protected List<UserPermissionDTO> toDto(List<UserPermissionEntity> entityList, UserAuthorityDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<UserPermissionDTO> list = new ArrayList<UserPermissionDTO>(entityList.size());
        for (UserPermissionEntity userPermissionEntity : entityList) {
            list.add(toDto(userPermissionEntity, parent));
        }

        return list;
    }

    @Override
    public Set<UserPermissionEntity> toEntity(Set<UserPermissionDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<UserPermissionEntity> set = new HashSet<UserPermissionEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (UserPermissionDTO userPermissionDTO : dtoList) {
            set.add(toEntity(userPermissionDTO));
        }

        return set;
    }

    protected Set<UserPermissionEntity> toEntity(Set<UserPermissionDTO> dtoList, UserAuthorityEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<UserPermissionEntity> set = new HashSet<UserPermissionEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (UserPermissionDTO userPermissionDTO : dtoList) {
            set.add(toEntity(userPermissionDTO, parent));
        }

        return set;
    }

    @Override
    public Set<UserPermissionDTO> toDto(Set<UserPermissionEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<UserPermissionDTO> set = new HashSet<UserPermissionDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (UserPermissionEntity userPermissionEntity : entityList) {
            set.add(toDto(userPermissionEntity));
        }

        return set;
    }

    protected Set<UserPermissionDTO> toDto(Set<UserPermissionEntity> entityList, UserAuthorityDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<UserPermissionDTO> set = new HashSet<UserPermissionDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (UserPermissionEntity userPermissionEntity : entityList) {
            set.add(toDto(userPermissionEntity, parent));
        }

        return set;
    }

}
