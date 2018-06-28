package io.avand.service.mapper;

import io.avand.domain.CompanyEntity;
import io.avand.domain.UserEntity;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CompanyMapper implements EntityMapper<CompanyDTO, CompanyEntity> {


    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private EvaluationCriteriaMapper evaluationCriteriaMapper;
    @Autowired
    private CompanyPipelineMapper companyPipelineMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public CompanyEntity toEntity(CompanyDTO dto) {
        if (dto == null) {
            return null;
        }

        CompanyEntity companyEntity = new CompanyEntity();

        companyEntity.setCreatedBy(dto.getCreatedBy());
        companyEntity.setCreatedDate(dto.getCreatedDate());
        companyEntity.setLastModifiedBy(dto.getLastModifiedBy());
        companyEntity.setLastModifiedDate(dto.getLastModifiedDate());
        companyEntity.setId(dto.getId());
        companyEntity.setName(dto.getName());
        companyEntity.setJobs(jobMapper.toEntity(dto.getJobs(), companyEntity));
        companyEntity.setEvaluationCriteria(evaluationCriteriaMapper.toEntity(dto.getEvaluationCriteria(), companyEntity));
        companyEntity.setCompanyPipelines(companyPipelineMapper.toEntity(dto.getCompanyPipelines()));
        companyEntity.setUser(userMapper.toEntity(dto.getUser()));

        return companyEntity;
    }

    protected CompanyEntity toEntity(CompanyDTO dto, UserEntity parent) {
        if (dto == null) {
            return null;
        }

        CompanyEntity companyEntity = new CompanyEntity();

        companyEntity.setCreatedBy(dto.getCreatedBy());
        companyEntity.setCreatedDate(dto.getCreatedDate());
        companyEntity.setLastModifiedBy(dto.getLastModifiedBy());
        companyEntity.setLastModifiedDate(dto.getLastModifiedDate());
        companyEntity.setId(dto.getId());
        companyEntity.setName(dto.getName());
        companyEntity.setJobs(jobMapper.toEntity(dto.getJobs(), companyEntity));
        companyEntity.setEvaluationCriteria(evaluationCriteriaMapper.toEntity(dto.getEvaluationCriteria(), companyEntity));
        companyEntity.setCompanyPipelines(companyPipelineMapper.toEntity(dto.getCompanyPipelines()));
        companyEntity.setUser(parent);

        return companyEntity;
    }

    @Override
    public CompanyDTO toDto(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }

        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setCreatedBy(entity.getCreatedBy());
        companyDTO.setCreatedDate(entity.getCreatedDate());
        companyDTO.setLastModifiedBy(entity.getLastModifiedBy());
        companyDTO.setLastModifiedDate(entity.getLastModifiedDate());
        companyDTO.setId(entity.getId());
        companyDTO.setName(entity.getName());
        companyDTO.setJobs(jobMapper.toDto(entity.getJobs(), companyDTO));
        companyDTO.setEvaluationCriteria(evaluationCriteriaMapper.toDto(entity.getEvaluationCriteria(), companyDTO));
        companyDTO.setCompanyPipelines(companyPipelineMapper.toDto(entity.getCompanyPipelines()));
        companyDTO.setUser(userMapper.toDto(entity.getUser()));

        return companyDTO;
    }

    protected CompanyDTO toDto(CompanyEntity entity, UserDTO parent) {
        if (entity == null) {
            return null;
        }

        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setCreatedBy(entity.getCreatedBy());
        companyDTO.setCreatedDate(entity.getCreatedDate());
        companyDTO.setLastModifiedBy(entity.getLastModifiedBy());
        companyDTO.setLastModifiedDate(entity.getLastModifiedDate());
        companyDTO.setId(entity.getId());
        companyDTO.setName(entity.getName());
        companyDTO.setJobs(jobMapper.toDto(entity.getJobs(), companyDTO));
        companyDTO.setEvaluationCriteria(evaluationCriteriaMapper.toDto(entity.getEvaluationCriteria(), companyDTO));
        companyDTO.setCompanyPipelines(companyPipelineMapper.toDto(entity.getCompanyPipelines()));
        companyDTO.setUser(parent);

        return companyDTO;
    }

    @Override
    public List<CompanyEntity> toEntity(List<CompanyDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<CompanyEntity> list = new ArrayList<CompanyEntity>(dtoList.size());
        for (CompanyDTO companyDTO : dtoList) {
            list.add(toEntity(companyDTO));
        }

        return list;
    }

    protected List<CompanyEntity> toEntity(List<CompanyDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<CompanyEntity> list = new ArrayList<CompanyEntity>(dtoList.size());
        for (CompanyDTO companyDTO : dtoList) {
            list.add(toEntity(companyDTO, parent));
        }

        return list;
    }

    @Override
    public List<CompanyDTO> toDto(List<CompanyEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CompanyDTO> list = new ArrayList<CompanyDTO>(entityList.size());
        for (CompanyEntity companyEntity : entityList) {
            list.add(toDto(companyEntity));
        }

        return list;
    }

    protected List<CompanyDTO> toDto(List<CompanyEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<CompanyDTO> list = new ArrayList<CompanyDTO>(entityList.size());
        for (CompanyEntity companyEntity : entityList) {
            list.add(toDto(companyEntity, parent));
        }

        return list;
    }


    @Override
    public Set<CompanyEntity> toEntity(Set<CompanyDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<CompanyEntity> set = new HashSet<CompanyEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CompanyDTO companyDTO : dtoList) {
            set.add(toEntity(companyDTO));
        }

        return set;
    }

    protected Set<CompanyEntity> toEntity(Set<CompanyDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<CompanyEntity> set = new HashSet<CompanyEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CompanyDTO companyDTO : dtoList) {
            set.add(toEntity(companyDTO, parent));
        }

        return set;
    }

    @Override
    public Set<CompanyDTO> toDto(Set<CompanyEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<CompanyDTO> set = new HashSet<CompanyDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CompanyEntity companyEntity : entityList) {
            set.add(toDto(companyEntity));
        }

        return set;
    }

    protected Set<CompanyDTO> toDto(Set<CompanyEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<CompanyDTO> set = new HashSet<CompanyDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CompanyEntity companyEntity : entityList) {
            set.add(toDto(companyEntity, parent));
        }

        return set;
    }
}
