package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.CandidateScheduleEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CandidateScheduleDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CandidateScheduleMapper implements EntityMapper<CandidateScheduleDTO, CandidateScheduleEntity> {

    @Autowired
    private CandidateMapper candidateMapper;

    @Override
    public CandidateScheduleEntity toEntity(CandidateScheduleDTO dto) {
        if (dto == null) {
            return null;
        }

        CandidateScheduleEntity candidateScheduleEntity = new CandidateScheduleEntity();

        candidateScheduleEntity.setId(dto.getId());
        candidateScheduleEntity.setOwner(dto.getOwner());
        candidateScheduleEntity.setScheduleDate(dto.getScheduleDate());
        candidateScheduleEntity.setCandidate(candidateMapper.toEntity(dto.getCandidate()));

        return candidateScheduleEntity;
    }

    protected CandidateScheduleEntity toEntity(CandidateScheduleDTO dto, CandidateEntity parent) {
        if (dto == null) {
            return null;
        }

        CandidateScheduleEntity candidateScheduleEntity = new CandidateScheduleEntity();

        candidateScheduleEntity.setId(dto.getId());
        candidateScheduleEntity.setOwner(dto.getOwner());
        candidateScheduleEntity.setScheduleDate(dto.getScheduleDate());
        candidateScheduleEntity.setCandidate(parent);

        return candidateScheduleEntity;
    }

    @Override
    public CandidateScheduleDTO toDto(CandidateScheduleEntity entity) {
        if (entity == null) {
            return null;
        }

        CandidateScheduleDTO candidateScheduleDTO = new CandidateScheduleDTO();

        candidateScheduleDTO.setId(entity.getId());
        candidateScheduleDTO.setOwner(entity.getOwner());
        candidateScheduleDTO.setScheduleDate(entity.getScheduleDate());
        candidateScheduleDTO.setCandidate(candidateMapper.toDto(entity.getCandidate()));

        return candidateScheduleDTO;
    }

    protected CandidateScheduleDTO toDto(CandidateScheduleEntity entity, CandidateDTO parent) {
        if (entity == null) {
            return null;
        }

        CandidateScheduleDTO candidateScheduleDTO = new CandidateScheduleDTO();

        candidateScheduleDTO.setId(entity.getId());
        candidateScheduleDTO.setOwner(entity.getOwner());
        candidateScheduleDTO.setScheduleDate(entity.getScheduleDate());
        candidateScheduleDTO.setCandidate(parent);

        return candidateScheduleDTO;
    }

    @Override
    public List<CandidateScheduleEntity> toEntity(List<CandidateScheduleDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<CandidateScheduleEntity> list = new ArrayList<CandidateScheduleEntity>(dtoList.size());
        for (CandidateScheduleDTO candidateScheduleDTO : dtoList) {
            list.add(toEntity(candidateScheduleDTO));
        }

        return list;
    }

    protected List<CandidateScheduleEntity> toEntity(List<CandidateScheduleDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<CandidateScheduleEntity> list = new ArrayList<CandidateScheduleEntity>(dtoList.size());
        for (CandidateScheduleDTO candidateScheduleDTO : dtoList) {
            list.add(toEntity(candidateScheduleDTO, parent));
        }

        return list;
    }

    @Override
    public List<CandidateScheduleDTO> toDto(List<CandidateScheduleEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CandidateScheduleDTO> list = new ArrayList<CandidateScheduleDTO>(entityList.size());
        for (CandidateScheduleEntity candidateScheduleEntity : entityList) {
            list.add(toDto(candidateScheduleEntity));
        }

        return list;
    }

    protected List<CandidateScheduleDTO> toDto(List<CandidateScheduleEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<CandidateScheduleDTO> list = new ArrayList<CandidateScheduleDTO>(entityList.size());
        for (CandidateScheduleEntity candidateScheduleEntity : entityList) {
            list.add(toDto(candidateScheduleEntity, parent));
        }

        return list;
    }

    @Override
    public Set<CandidateScheduleEntity> toEntity(Set<CandidateScheduleDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<CandidateScheduleEntity> set = new HashSet<CandidateScheduleEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CandidateScheduleDTO candidateScheduleDTO : dtoList) {
            set.add(toEntity(candidateScheduleDTO));
        }

        return set;
    }

    protected Set<CandidateScheduleEntity> toEntity(Set<CandidateScheduleDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<CandidateScheduleEntity> set = new HashSet<CandidateScheduleEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CandidateScheduleDTO candidateScheduleDTO : dtoList) {
            set.add(toEntity(candidateScheduleDTO, parent));
        }

        return set;
    }

    @Override
    public Set<CandidateScheduleDTO> toDto(Set<CandidateScheduleEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<CandidateScheduleDTO> set = new HashSet<CandidateScheduleDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CandidateScheduleEntity candidateScheduleEntity : entityList) {
            set.add(toDto(candidateScheduleEntity));
        }

        return set;
    }

    protected Set<CandidateScheduleDTO> toDto(Set<CandidateScheduleEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<CandidateScheduleDTO> set = new HashSet<CandidateScheduleDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CandidateScheduleEntity candidateScheduleEntity : entityList) {
            set.add(toDto(candidateScheduleEntity, parent));
        }

        return set;
    }

}
