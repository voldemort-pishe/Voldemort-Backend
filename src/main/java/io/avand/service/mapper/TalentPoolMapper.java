package io.avand.service.mapper;

import io.avand.domain.TalentPoolEntity;
import io.avand.domain.UserEntity;
import io.avand.service.dto.TalentPoolDTO;
import io.avand.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TalentPoolMapper implements EntityMapper<TalentPoolDTO, TalentPoolEntity> {

    @Autowired
    private UserMapper userMapper;

    @Override
    public TalentPoolEntity toEntity(TalentPoolDTO dto) {
        if (dto == null) {
            return null;
        }

        TalentPoolEntity talentPoolEntity = new TalentPoolEntity();

        talentPoolEntity.setId(dto.getId());
        talentPoolEntity.setLastName(dto.getLastName());
        talentPoolEntity.setFileId(dto.getFileId());
        talentPoolEntity.setState(dto.getState());
        talentPoolEntity.setCellphone(dto.getCellphone());
        talentPoolEntity.setEmail(dto.getEmail());
        talentPoolEntity.setUser(userMapper.toEntity(dto.getUser()));

        return talentPoolEntity;
    }

    protected TalentPoolEntity toEntity(TalentPoolDTO dto, UserEntity parent) {
        if (dto == null) {
            return null;
        }

        TalentPoolEntity talentPoolEntity = new TalentPoolEntity();

        talentPoolEntity.setId(dto.getId());
        talentPoolEntity.setLastName(dto.getLastName());
        talentPoolEntity.setFileId(dto.getFileId());
        talentPoolEntity.setState(dto.getState());
        talentPoolEntity.setCellphone(dto.getCellphone());
        talentPoolEntity.setEmail(dto.getEmail());
        talentPoolEntity.setUser(parent);

        return talentPoolEntity;
    }

    @Override
    public TalentPoolDTO toDto(TalentPoolEntity entity) {
        if (entity == null) {
            return null;
        }

        TalentPoolDTO talentPoolDTO = new TalentPoolDTO();

        talentPoolDTO.setId(entity.getId());
        talentPoolDTO.setLastName(entity.getLastName());
        talentPoolDTO.setFileId(entity.getFileId());
        talentPoolDTO.setState(entity.getState());
        talentPoolDTO.setCellphone(entity.getCellphone());
        talentPoolDTO.setEmail(entity.getEmail());
        talentPoolDTO.setUser(userMapper.toDto(entity.getUser()));

        return talentPoolDTO;
    }

    protected TalentPoolDTO toDto(TalentPoolEntity entity, UserDTO parent) {
        if (entity == null) {
            return null;
        }

        TalentPoolDTO talentPoolDTO = new TalentPoolDTO();

        talentPoolDTO.setId(entity.getId());
        talentPoolDTO.setLastName(entity.getLastName());
        talentPoolDTO.setFileId(entity.getFileId());
        talentPoolDTO.setState(entity.getState());
        talentPoolDTO.setCellphone(entity.getCellphone());
        talentPoolDTO.setEmail(entity.getEmail());
        talentPoolDTO.setUser(parent);

        return talentPoolDTO;
    }

    @Override
    public List<TalentPoolEntity> toEntity(List<TalentPoolDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<TalentPoolEntity> list = new ArrayList<TalentPoolEntity>(dtoList.size());
        for (TalentPoolDTO talentPoolDTO : dtoList) {
            list.add(toEntity(talentPoolDTO));
        }

        return list;
    }

    protected List<TalentPoolEntity> toEntity(List<TalentPoolDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<TalentPoolEntity> list = new ArrayList<TalentPoolEntity>(dtoList.size());
        for (TalentPoolDTO talentPoolDTO : dtoList) {
            list.add(toEntity(talentPoolDTO, parent));
        }

        return list;
    }

    @Override
    public List<TalentPoolDTO> toDto(List<TalentPoolEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<TalentPoolDTO> list = new ArrayList<TalentPoolDTO>(entityList.size());
        for (TalentPoolEntity talentPoolEntity : entityList) {
            list.add(toDto(talentPoolEntity));
        }

        return list;
    }

    protected List<TalentPoolDTO> toDto(List<TalentPoolEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<TalentPoolDTO> list = new ArrayList<TalentPoolDTO>(entityList.size());
        for (TalentPoolEntity talentPoolEntity : entityList) {
            list.add(toDto(talentPoolEntity, parent));
        }

        return list;
    }

    @Override
    public Set<TalentPoolEntity> toEntity(Set<TalentPoolDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<TalentPoolEntity> set = new HashSet<TalentPoolEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (TalentPoolDTO talentPoolDTO : dtoList) {
            set.add(toEntity(talentPoolDTO));
        }

        return set;
    }

    protected Set<TalentPoolEntity> toEntity(Set<TalentPoolDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<TalentPoolEntity> set = new HashSet<TalentPoolEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (TalentPoolDTO talentPoolDTO : dtoList) {
            set.add(toEntity(talentPoolDTO, parent));
        }

        return set;
    }

    @Override
    public Set<TalentPoolDTO> toDto(Set<TalentPoolEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<TalentPoolDTO> set = new HashSet<TalentPoolDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (TalentPoolEntity talentPoolEntity : entityList) {
            set.add(toDto(talentPoolEntity));
        }

        return set;
    }

    protected Set<TalentPoolDTO> toDto(Set<TalentPoolEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<TalentPoolDTO> set = new HashSet<TalentPoolDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (TalentPoolEntity talentPoolEntity : entityList) {
            set.add(toDto(talentPoolEntity, parent));
        }

        return set;
    }

}
