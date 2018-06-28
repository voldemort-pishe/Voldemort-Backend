package io.avand.service.mapper;

import io.avand.domain.CompanyEntity;
import io.avand.domain.CompanyPipelineEntity;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.CompanyPipelineDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CompanyPipelineMapper implements EntityMapper<CompanyPipelineDTO, CompanyPipelineEntity> {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public CompanyPipelineEntity toEntity(CompanyPipelineDTO dto) {
        if (dto == null) {
            return null;
        }

        CompanyPipelineEntity companyPipelineEntity = new CompanyPipelineEntity();

        companyPipelineEntity.setId(dto.getId());
        companyPipelineEntity.setTitle(dto.getTitle());
        companyPipelineEntity.setWeight(dto.getWeight());
        companyPipelineEntity.setCompany(companyMapper.toEntity(dto.getCompany()));

        return companyPipelineEntity;
    }

    protected CompanyPipelineEntity toEntity(CompanyPipelineDTO dto, CompanyEntity parent) {
        if (dto == null) {
            return null;
        }

        CompanyPipelineEntity companyPipelineEntity = new CompanyPipelineEntity();

        companyPipelineEntity.setId(dto.getId());
        companyPipelineEntity.setTitle(dto.getTitle());
        companyPipelineEntity.setWeight(dto.getWeight());
        companyPipelineEntity.setCompany(parent);

        return companyPipelineEntity;
    }

    @Override
    public CompanyPipelineDTO toDto(CompanyPipelineEntity entity) {
        if (entity == null) {
            return null;
        }

        CompanyPipelineDTO companyPipelineDTO = new CompanyPipelineDTO();

        companyPipelineDTO.setId(entity.getId());
        companyPipelineDTO.setTitle(entity.getTitle());
        companyPipelineDTO.setWeight(entity.getWeight());
        companyPipelineDTO.setCompany(companyMapper.toDto(entity.getCompany()));

        return companyPipelineDTO;
    }


    protected CompanyPipelineDTO toDto(CompanyPipelineEntity entity, CompanyDTO parent) {
        if (entity == null) {
            return null;
        }

        CompanyPipelineDTO companyPipelineDTO = new CompanyPipelineDTO();

        companyPipelineDTO.setId(entity.getId());
        companyPipelineDTO.setTitle(entity.getTitle());
        companyPipelineDTO.setWeight(entity.getWeight());
        companyPipelineDTO.setCompany(parent);

        return companyPipelineDTO;
    }

    @Override
    public List<CompanyPipelineEntity> toEntity(List<CompanyPipelineDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<CompanyPipelineEntity> list = new ArrayList<CompanyPipelineEntity>(dtoList.size());
        for (CompanyPipelineDTO companyPipelineDTO : dtoList) {
            list.add(toEntity(companyPipelineDTO));
        }

        return list;
    }

    protected List<CompanyPipelineEntity> toEntity(List<CompanyPipelineDTO> dtoList, CompanyEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<CompanyPipelineEntity> list = new ArrayList<CompanyPipelineEntity>(dtoList.size());
        for (CompanyPipelineDTO companyPipelineDTO : dtoList) {
            list.add(toEntity(companyPipelineDTO, parent));
        }

        return list;
    }

    @Override
    public List<CompanyPipelineDTO> toDto(List<CompanyPipelineEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CompanyPipelineDTO> list = new ArrayList<CompanyPipelineDTO>(entityList.size());
        for (CompanyPipelineEntity companyPipelineEntity : entityList) {
            list.add(toDto(companyPipelineEntity));
        }

        return list;
    }

    protected List<CompanyPipelineDTO> toDto(List<CompanyPipelineEntity> entityList, CompanyDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<CompanyPipelineDTO> list = new ArrayList<CompanyPipelineDTO>(entityList.size());
        for (CompanyPipelineEntity companyPipelineEntity : entityList) {
            list.add(toDto(companyPipelineEntity, parent));
        }

        return list;
    }

    @Override
    public Set<CompanyPipelineEntity> toEntity(Set<CompanyPipelineDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<CompanyPipelineEntity> set = new HashSet<CompanyPipelineEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CompanyPipelineDTO companyPipelineDTO : dtoList) {
            set.add(toEntity(companyPipelineDTO));
        }

        return set;
    }

    protected Set<CompanyPipelineEntity> toEntity(Set<CompanyPipelineDTO> dtoList, CompanyEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<CompanyPipelineEntity> set = new HashSet<CompanyPipelineEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CompanyPipelineDTO companyPipelineDTO : dtoList) {
            set.add(toEntity(companyPipelineDTO, parent));
        }

        return set;
    }

    @Override
    public Set<CompanyPipelineDTO> toDto(Set<CompanyPipelineEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<CompanyPipelineDTO> set = new HashSet<CompanyPipelineDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CompanyPipelineEntity companyPipelineEntity : entityList) {
            set.add(toDto(companyPipelineEntity));
        }

        return set;
    }

    protected Set<CompanyPipelineDTO> toDto(Set<CompanyPipelineEntity> entityList, CompanyDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<CompanyPipelineDTO> set = new HashSet<CompanyPipelineDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CompanyPipelineEntity companyPipelineEntity : entityList) {
            set.add(toDto(companyPipelineEntity, parent));
        }

        return set;
    }

}
