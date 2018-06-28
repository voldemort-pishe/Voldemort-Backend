package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.CandidateEvaluationCriteriaEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CandidateEvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CandidateEvaluationCriteriaMapper implements EntityMapper<CandidateEvaluationCriteriaDTO, CandidateEvaluationCriteriaEntity> {

    @Autowired
    private CandidateMapper candidateMapper;

    @Override
    public CandidateEvaluationCriteriaEntity toEntity(CandidateEvaluationCriteriaDTO dto) {
        if (dto == null) {
            return null;
        }

        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity = new CandidateEvaluationCriteriaEntity();

        candidateEvaluationCriteriaEntity.setId(dto.getId());
        candidateEvaluationCriteriaEntity.setUserId(dto.getUserId());
        candidateEvaluationCriteriaEntity.setUserComment(dto.getUserComment());
        candidateEvaluationCriteriaEntity.setEvaluationCriteriaId(dto.getEvaluationCriteriaId());
        candidateEvaluationCriteriaEntity.setCandidate(candidateMapper.toEntity(dto.getCandidateDTO()));

        return candidateEvaluationCriteriaEntity;
    }

    protected CandidateEvaluationCriteriaEntity toEntity(CandidateEvaluationCriteriaDTO dto, CandidateEntity parent) {
        if (dto == null) {
            return null;
        }

        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity = new CandidateEvaluationCriteriaEntity();

        candidateEvaluationCriteriaEntity.setId(dto.getId());
        candidateEvaluationCriteriaEntity.setUserId(dto.getUserId());
        candidateEvaluationCriteriaEntity.setUserComment(dto.getUserComment());
        candidateEvaluationCriteriaEntity.setEvaluationCriteriaId(dto.getEvaluationCriteriaId());
        candidateEvaluationCriteriaEntity.setCandidate(parent);

        return candidateEvaluationCriteriaEntity;
    }

    @Override
    public CandidateEvaluationCriteriaDTO toDto(CandidateEvaluationCriteriaEntity entity) {
        if (entity == null) {
            return null;
        }

        CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO = new CandidateEvaluationCriteriaDTO();

        candidateEvaluationCriteriaDTO.setId(entity.getId());
        candidateEvaluationCriteriaDTO.setUserId(entity.getUserId());
        candidateEvaluationCriteriaDTO.setUserComment(entity.getUserComment());
        candidateEvaluationCriteriaDTO.setEvaluationCriteriaId(entity.getEvaluationCriteriaId());
        candidateEvaluationCriteriaDTO.setCandidateDTO(candidateMapper.toDto(entity.getCandidate()));

        return candidateEvaluationCriteriaDTO;
    }

    protected CandidateEvaluationCriteriaDTO toDto(CandidateEvaluationCriteriaEntity entity, CandidateDTO parent) {
        if (entity == null) {
            return null;
        }

        CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO = new CandidateEvaluationCriteriaDTO();

        candidateEvaluationCriteriaDTO.setId(entity.getId());
        candidateEvaluationCriteriaDTO.setUserId(entity.getUserId());
        candidateEvaluationCriteriaDTO.setUserComment(entity.getUserComment());
        candidateEvaluationCriteriaDTO.setEvaluationCriteriaId(entity.getEvaluationCriteriaId());
        candidateEvaluationCriteriaDTO.setCandidateDTO(parent);

        return candidateEvaluationCriteriaDTO;
    }

    @Override
    public List<CandidateEvaluationCriteriaEntity> toEntity(List<CandidateEvaluationCriteriaDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<CandidateEvaluationCriteriaEntity> list = new ArrayList<CandidateEvaluationCriteriaEntity>(dtoList.size());
        for (CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO : dtoList) {
            list.add(toEntity(candidateEvaluationCriteriaDTO));
        }

        return list;
    }

    protected List<CandidateEvaluationCriteriaEntity> toEntity(List<CandidateEvaluationCriteriaDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<CandidateEvaluationCriteriaEntity> list = new ArrayList<CandidateEvaluationCriteriaEntity>(dtoList.size());
        for (CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO : dtoList) {
            list.add(toEntity(candidateEvaluationCriteriaDTO, parent));
        }

        return list;
    }

    @Override
    public List<CandidateEvaluationCriteriaDTO> toDto(List<CandidateEvaluationCriteriaEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CandidateEvaluationCriteriaDTO> list = new ArrayList<CandidateEvaluationCriteriaDTO>(entityList.size());
        for (CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity : entityList) {
            list.add(toDto(candidateEvaluationCriteriaEntity));
        }

        return list;
    }

    protected List<CandidateEvaluationCriteriaDTO> toDto(List<CandidateEvaluationCriteriaEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<CandidateEvaluationCriteriaDTO> list = new ArrayList<CandidateEvaluationCriteriaDTO>(entityList.size());
        for (CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity : entityList) {
            list.add(toDto(candidateEvaluationCriteriaEntity, parent));
        }

        return list;
    }

    @Override
    public Set<CandidateEvaluationCriteriaEntity> toEntity(Set<CandidateEvaluationCriteriaDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<CandidateEvaluationCriteriaEntity> set = new HashSet<CandidateEvaluationCriteriaEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO : dtoList) {
            set.add(toEntity(candidateEvaluationCriteriaDTO));
        }

        return set;
    }

    protected Set<CandidateEvaluationCriteriaEntity> toEntity(Set<CandidateEvaluationCriteriaDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<CandidateEvaluationCriteriaEntity> set = new HashSet<CandidateEvaluationCriteriaEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO : dtoList) {
            set.add(toEntity(candidateEvaluationCriteriaDTO, parent));
        }

        return set;
    }

    @Override
    public Set<CandidateEvaluationCriteriaDTO> toDto(Set<CandidateEvaluationCriteriaEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<CandidateEvaluationCriteriaDTO> set = new HashSet<CandidateEvaluationCriteriaDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity : entityList) {
            set.add(toDto(candidateEvaluationCriteriaEntity));
        }

        return set;
    }

    protected Set<CandidateEvaluationCriteriaDTO> toDto(Set<CandidateEvaluationCriteriaEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<CandidateEvaluationCriteriaDTO> set = new HashSet<CandidateEvaluationCriteriaDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity : entityList) {
            set.add(toDto(candidateEvaluationCriteriaEntity, parent));
        }

        return set;
    }
}
