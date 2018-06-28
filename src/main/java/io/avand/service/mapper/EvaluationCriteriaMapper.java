package io.avand.service.mapper;

import io.avand.domain.CompanyEntity;
import io.avand.domain.EvaluationCriteriaEntity;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.EvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class EvaluationCriteriaMapper implements EntityMapper<EvaluationCriteriaDTO, EvaluationCriteriaEntity> {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public EvaluationCriteriaEntity toEntity(EvaluationCriteriaDTO dto) {
        if (dto == null) {
            return null;
        }

        EvaluationCriteriaEntity evaluationCriteriaEntity = new EvaluationCriteriaEntity();

        evaluationCriteriaEntity.setId(dto.getId());
        evaluationCriteriaEntity.setTitle(dto.getTitle());
        evaluationCriteriaEntity.setDescription(dto.getDescription());
        evaluationCriteriaEntity.setCompany(companyMapper.toEntity(dto.getCompany()));

        return evaluationCriteriaEntity;
    }

    protected EvaluationCriteriaEntity toEntity(EvaluationCriteriaDTO dto, CompanyEntity parent) {
        if (dto == null) {
            return null;
        }

        EvaluationCriteriaEntity evaluationCriteriaEntity = new EvaluationCriteriaEntity();

        evaluationCriteriaEntity.setId(dto.getId());
        evaluationCriteriaEntity.setTitle(dto.getTitle());
        evaluationCriteriaEntity.setDescription(dto.getDescription());
        evaluationCriteriaEntity.setCompany(parent);

        return evaluationCriteriaEntity;
    }

    @Override
    public EvaluationCriteriaDTO toDto(EvaluationCriteriaEntity entity) {
        if (entity == null) {
            return null;
        }

        EvaluationCriteriaDTO evaluationCriteriaDTO = new EvaluationCriteriaDTO();

        evaluationCriteriaDTO.setId(entity.getId());
        evaluationCriteriaDTO.setTitle(entity.getTitle());
        evaluationCriteriaDTO.setDescription(entity.getDescription());
        evaluationCriteriaDTO.setCompany(companyMapper.toDto(entity.getCompany()));

        return evaluationCriteriaDTO;
    }

    protected EvaluationCriteriaDTO toDto(EvaluationCriteriaEntity entity, CompanyDTO parent) {
        if (entity == null) {
            return null;
        }

        EvaluationCriteriaDTO evaluationCriteriaDTO = new EvaluationCriteriaDTO();

        evaluationCriteriaDTO.setId(entity.getId());
        evaluationCriteriaDTO.setTitle(entity.getTitle());
        evaluationCriteriaDTO.setDescription(entity.getDescription());
        evaluationCriteriaDTO.setCompany(parent);

        return evaluationCriteriaDTO;
    }

    @Override
    public List<EvaluationCriteriaEntity> toEntity(List<EvaluationCriteriaDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<EvaluationCriteriaEntity> list = new ArrayList<EvaluationCriteriaEntity>(dtoList.size());
        for (EvaluationCriteriaDTO evaluationCriteriaDTO : dtoList) {
            list.add(toEntity(evaluationCriteriaDTO));
        }

        return list;
    }

    protected List<EvaluationCriteriaEntity> toEntity(List<EvaluationCriteriaDTO> dtoList, CompanyEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<EvaluationCriteriaEntity> list = new ArrayList<EvaluationCriteriaEntity>(dtoList.size());
        for (EvaluationCriteriaDTO evaluationCriteriaDTO : dtoList) {
            list.add(toEntity(evaluationCriteriaDTO, parent));
        }

        return list;
    }

    @Override
    public List<EvaluationCriteriaDTO> toDto(List<EvaluationCriteriaEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<EvaluationCriteriaDTO> list = new ArrayList<EvaluationCriteriaDTO>(entityList.size());
        for (EvaluationCriteriaEntity evaluationCriteriaEntity : entityList) {
            list.add(toDto(evaluationCriteriaEntity));
        }

        return list;
    }

    protected List<EvaluationCriteriaDTO> toDto(List<EvaluationCriteriaEntity> entityList, CompanyDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<EvaluationCriteriaDTO> list = new ArrayList<EvaluationCriteriaDTO>(entityList.size());
        for (EvaluationCriteriaEntity evaluationCriteriaEntity : entityList) {
            list.add(toDto(evaluationCriteriaEntity, parent));
        }

        return list;
    }

    @Override
    public Set<EvaluationCriteriaEntity> toEntity(Set<EvaluationCriteriaDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<EvaluationCriteriaEntity> set = new HashSet<EvaluationCriteriaEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (EvaluationCriteriaDTO evaluationCriteriaDTO : dtoList) {
            set.add(toEntity(evaluationCriteriaDTO));
        }

        return set;
    }

    protected Set<EvaluationCriteriaEntity> toEntity(Set<EvaluationCriteriaDTO> dtoList, CompanyEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<EvaluationCriteriaEntity> set = new HashSet<EvaluationCriteriaEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (EvaluationCriteriaDTO evaluationCriteriaDTO : dtoList) {
            set.add(toEntity(evaluationCriteriaDTO, parent));
        }

        return set;
    }

    @Override
    public Set<EvaluationCriteriaDTO> toDto(Set<EvaluationCriteriaEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<EvaluationCriteriaDTO> set = new HashSet<EvaluationCriteriaDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (EvaluationCriteriaEntity evaluationCriteriaEntity : entityList) {
            set.add(toDto(evaluationCriteriaEntity));
        }

        return set;
    }

    protected Set<EvaluationCriteriaDTO> toDto(Set<EvaluationCriteriaEntity> entityList, CompanyDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<EvaluationCriteriaDTO> set = new HashSet<EvaluationCriteriaDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (EvaluationCriteriaEntity evaluationCriteriaEntity : entityList) {
            set.add(toDto(evaluationCriteriaEntity, parent));
        }

        return set;
    }

}
