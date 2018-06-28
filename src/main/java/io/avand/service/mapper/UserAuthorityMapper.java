package io.avand.service.mapper;

import io.avand.domain.UserAuthorityEntity;
import io.avand.domain.UserEntity;
import io.avand.service.dto.UserAuthorityDTO;
import io.avand.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserAuthorityMapper implements EntityMapper<UserAuthorityDTO, UserAuthorityEntity> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Override
    public UserAuthorityEntity toEntity(UserAuthorityDTO dto) {
        if (dto == null) {
            return null;
        }

        UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();

        userAuthorityEntity.setId(dto.getId());
        userAuthorityEntity.setAuthorityName(dto.getAuthorityName());
        userAuthorityEntity.setUserPermissions(userPermissionMapper.toEntity(dto.getUserPermissions(), userAuthorityEntity));
        userAuthorityEntity.setUser(userMapper.toEntity(dto.getUser()));

        return userAuthorityEntity;
    }

    protected UserAuthorityEntity toEntity(UserAuthorityDTO dto, UserEntity parent) {
        if (dto == null) {
            return null;
        }

        UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();

        userAuthorityEntity.setId(dto.getId());
        userAuthorityEntity.setAuthorityName(dto.getAuthorityName());
        userAuthorityEntity.setUserPermissions(userPermissionMapper.toEntity(dto.getUserPermissions(), userAuthorityEntity));
        userAuthorityEntity.setUser(parent);

        return userAuthorityEntity;
    }

    @Override
    public UserAuthorityDTO toDto(UserAuthorityEntity entity) {
        if (entity == null) {
            return null;
        }

        UserAuthorityDTO userAuthorityDTO = new UserAuthorityDTO();

        userAuthorityDTO.setId(entity.getId());
        userAuthorityDTO.setAuthorityName(entity.getAuthorityName());
        userAuthorityDTO.setUserPermissions(userPermissionMapper.toDto(entity.getUserPermissions(), userAuthorityDTO));
        userAuthorityDTO.setUser(userMapper.toDto(entity.getUser()));

        return userAuthorityDTO;
    }

    protected UserAuthorityDTO toDto(UserAuthorityEntity entity, UserDTO parent) {
        if (entity == null) {
            return null;
        }

        UserAuthorityDTO userAuthorityDTO = new UserAuthorityDTO();

        userAuthorityDTO.setId(entity.getId());
        userAuthorityDTO.setAuthorityName(entity.getAuthorityName());
        userAuthorityDTO.setUserPermissions(userPermissionMapper.toDto(entity.getUserPermissions(), userAuthorityDTO));
        userAuthorityDTO.setUser(parent);

        return userAuthorityDTO;
    }

    @Override
    public List<UserAuthorityEntity> toEntity(List<UserAuthorityDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<UserAuthorityEntity> list = new ArrayList<UserAuthorityEntity>(dtoList.size());
        for (UserAuthorityDTO userAuthorityDTO : dtoList) {
            list.add(toEntity(userAuthorityDTO));
        }

        return list;
    }

    protected List<UserAuthorityEntity> toEntity(List<UserAuthorityDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<UserAuthorityEntity> list = new ArrayList<UserAuthorityEntity>(dtoList.size());
        for (UserAuthorityDTO userAuthorityDTO : dtoList) {
            list.add(toEntity(userAuthorityDTO, parent));
        }

        return list;
    }

    @Override
    public List<UserAuthorityDTO> toDto(List<UserAuthorityEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<UserAuthorityDTO> list = new ArrayList<UserAuthorityDTO>(entityList.size());
        for (UserAuthorityEntity userAuthorityEntity : entityList) {
            list.add(toDto(userAuthorityEntity));
        }

        return list;
    }

    protected List<UserAuthorityDTO> toDto(List<UserAuthorityEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<UserAuthorityDTO> list = new ArrayList<UserAuthorityDTO>(entityList.size());
        for (UserAuthorityEntity userAuthorityEntity : entityList) {
            list.add(toDto(userAuthorityEntity, parent));
        }

        return list;
    }

    @Override
    public Set<UserAuthorityEntity> toEntity(Set<UserAuthorityDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<UserAuthorityEntity> set = new HashSet<UserAuthorityEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (UserAuthorityDTO userAuthorityDTO : dtoList) {
            set.add(toEntity(userAuthorityDTO));
        }

        return set;
    }

    protected Set<UserAuthorityEntity> toEntity(Set<UserAuthorityDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<UserAuthorityEntity> set = new HashSet<UserAuthorityEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (UserAuthorityDTO userAuthorityDTO : dtoList) {
            set.add(toEntity(userAuthorityDTO, parent));
        }

        return set;
    }

    @Override
    public Set<UserAuthorityDTO> toDto(Set<UserAuthorityEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<UserAuthorityDTO> set = new HashSet<UserAuthorityDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (UserAuthorityEntity userAuthorityEntity : entityList) {
            set.add(toDto(userAuthorityEntity));
        }

        return set;
    }

    protected Set<UserAuthorityDTO> toDto(Set<UserAuthorityEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<UserAuthorityDTO> set = new HashSet<UserAuthorityDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (UserAuthorityEntity userAuthorityEntity : entityList) {
            set.add(toDto(userAuthorityEntity, parent));
        }

        return set;
    }
}
