package io.avand.service.mapper;

import io.avand.domain.CompanyEntity;
import io.avand.domain.JobEntity;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JobMapper implements EntityMapper<JobDTO, JobEntity> {

    @Autowired
    private CandidateMapper candidateMapper;
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public JobEntity toEntity(JobDTO dto) {
        if (dto == null) {
            return null;
        }

        JobEntity jobEntity = new JobEntity();

        jobEntity.setCreatedBy(dto.getCreatedBy());
        jobEntity.setCreatedDate(dto.getCreatedDate());
        jobEntity.setLastModifiedBy(dto.getLastModifiedBy());
        jobEntity.setLastModifiedDate(dto.getLastModifiedDate());
        jobEntity.setId(dto.getId());
        jobEntity.setName(dto.getName());
        jobEntity.setCandidate(candidateMapper.toEntity(dto.getCandidate(),jobEntity));
        jobEntity.setCompany(companyMapper.toEntity(dto.getCompany()));

        return jobEntity;
    }

    protected JobEntity toEntity(JobDTO dto, CompanyEntity parent) {
        if (dto == null) {
            return null;
        }

        JobEntity jobEntity = new JobEntity();

        jobEntity.setCreatedBy(dto.getCreatedBy());
        jobEntity.setCreatedDate(dto.getCreatedDate());
        jobEntity.setLastModifiedBy(dto.getLastModifiedBy());
        jobEntity.setLastModifiedDate(dto.getLastModifiedDate());
        jobEntity.setId(dto.getId());
        jobEntity.setName(dto.getName());
        jobEntity.setCandidate(candidateMapper.toEntity(dto.getCandidate(),jobEntity));
        jobEntity.setCompany(parent);

        return jobEntity;
    }

    @Override
    public JobDTO toDto(JobEntity entity) {
        if (entity == null) {
            return null;
        }

        JobDTO jobDTO = new JobDTO();

        jobDTO.setCreatedBy(entity.getCreatedBy());
        jobDTO.setCreatedDate(entity.getCreatedDate());
        jobDTO.setLastModifiedBy(entity.getLastModifiedBy());
        jobDTO.setLastModifiedDate(entity.getLastModifiedDate());
        jobDTO.setId(entity.getId());
        jobDTO.setName(entity.getName());
        jobDTO.setCandidate(candidateMapper.toDto(entity.getCandidate(),jobDTO));
        jobDTO.setCompany(companyMapper.toDto(entity.getCompany()));

        return jobDTO;
    }

    protected JobDTO toDto(JobEntity entity, CompanyDTO parent) {
        if (entity == null) {
            return null;
        }

        JobDTO jobDTO = new JobDTO();

        jobDTO.setCreatedBy(entity.getCreatedBy());
        jobDTO.setCreatedDate(entity.getCreatedDate());
        jobDTO.setLastModifiedBy(entity.getLastModifiedBy());
        jobDTO.setLastModifiedDate(entity.getLastModifiedDate());
        jobDTO.setId(entity.getId());
        jobDTO.setName(entity.getName());
        jobDTO.setCandidate(candidateMapper.toDto(entity.getCandidate(),jobDTO));
        jobDTO.setCompany(parent);

        return jobDTO;
    }

    @Override
    public List<JobEntity> toEntity(List<JobDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<JobEntity> list = new ArrayList<JobEntity>(dtoList.size());
        for (JobDTO jobDTO : dtoList) {
            list.add(toEntity(jobDTO));
        }

        return list;
    }

    protected List<JobEntity> toEntity(List<JobDTO> dtoList,CompanyEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<JobEntity> list = new ArrayList<JobEntity>(dtoList.size());
        for (JobDTO jobDTO : dtoList) {
            list.add(toEntity(jobDTO,parent));
        }

        return list;
    }

    @Override
    public List<JobDTO> toDto(List<JobEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<JobDTO> list = new ArrayList<JobDTO>(entityList.size());
        for (JobEntity jobEntity : entityList) {
            list.add(toDto(jobEntity));
        }

        return list;
    }

    protected List<JobDTO> toDto(List<JobEntity> entityList,CompanyDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<JobDTO> list = new ArrayList<JobDTO>(entityList.size());
        for (JobEntity jobEntity : entityList) {
            list.add(toDto(jobEntity,parent));
        }

        return list;
    }

    @Override
    public Set<JobEntity> toEntity(Set<JobDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<JobEntity> set = new HashSet<JobEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (JobDTO jobDTO : dtoList) {
            set.add(toEntity(jobDTO));
        }

        return set;
    }

    protected Set<JobEntity> toEntity(Set<JobDTO> dtoList,CompanyEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<JobEntity> set = new HashSet<JobEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (JobDTO jobDTO : dtoList) {
            set.add(toEntity(jobDTO,parent));
        }

        return set;
    }

    @Override
    public Set<JobDTO> toDto(Set<JobEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<JobDTO> set = new HashSet<JobDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (JobEntity jobEntity : entityList) {
            set.add(toDto(jobEntity));
        }

        return set;
    }

    protected Set<JobDTO> toDto(Set<JobEntity> entityList,CompanyDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<JobDTO> set = new HashSet<JobDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (JobEntity jobEntity : entityList) {
            set.add(toDto(jobEntity,parent));
        }

        return set;
    }

}
