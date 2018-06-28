package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.FileEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.FileDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FileMapper implements EntityMapper<FileDTO, FileEntity> {

    @Autowired
    private CandidateMapper candidateMapper;

    @Override
    public FileEntity toEntity(FileDTO dto) {
        if (dto == null) {
            return null;
        }

        FileEntity fileEntity = new FileEntity();

        fileEntity.setId(dto.getId());
        fileEntity.setFilename(dto.getFilename());
        fileEntity.setCandidate(candidateMapper.toEntity(dto.getCandidate()));

        return fileEntity;
    }

    protected FileEntity toEntity(FileDTO dto, CandidateEntity parent) {
        if (dto == null) {
            return null;
        }

        FileEntity fileEntity = new FileEntity();

        fileEntity.setId(dto.getId());
        fileEntity.setFilename(dto.getFilename());
        fileEntity.setCandidate(parent);

        return fileEntity;
    }

    @Override
    public FileDTO toDto(FileEntity entity) {
        if (entity == null) {
            return null;
        }

        FileDTO fileDTO = new FileDTO();

        fileDTO.setId(entity.getId());
        fileDTO.setFilename(entity.getFilename());
        fileDTO.setCandidate(candidateMapper.toDto(entity.getCandidate()));

        return fileDTO;
    }

    protected FileDTO toDto(FileEntity entity, CandidateDTO parent) {
        if (entity == null) {
            return null;
        }

        FileDTO fileDTO = new FileDTO();

        fileDTO.setId(entity.getId());
        fileDTO.setFilename(entity.getFilename());
        fileDTO.setCandidate(parent);

        return fileDTO;
    }

    @Override
    public List<FileEntity> toEntity(List<FileDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<FileEntity> list = new ArrayList<FileEntity>(dtoList.size());
        for (FileDTO fileDTO : dtoList) {
            list.add(toEntity(fileDTO));
        }

        return list;
    }

    protected List<FileEntity> toEntity(List<FileDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<FileEntity> list = new ArrayList<FileEntity>(dtoList.size());
        for (FileDTO fileDTO : dtoList) {
            list.add(toEntity(fileDTO, parent));
        }

        return list;
    }

    @Override
    public List<FileDTO> toDto(List<FileEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<FileDTO> list = new ArrayList<FileDTO>(entityList.size());
        for (FileEntity fileEntity : entityList) {
            list.add(toDto(fileEntity));
        }

        return list;
    }

    protected List<FileDTO> toDto(List<FileEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<FileDTO> list = new ArrayList<FileDTO>(entityList.size());
        for (FileEntity fileEntity : entityList) {
            list.add(toDto(fileEntity, parent));
        }

        return list;
    }

    @Override
    public Set<FileEntity> toEntity(Set<FileDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<FileEntity> set = new HashSet<FileEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (FileDTO fileDTO : dtoList) {
            set.add(toEntity(fileDTO));
        }

        return set;
    }

    protected Set<FileEntity> toEntity(Set<FileDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<FileEntity> set = new HashSet<FileEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (FileDTO fileDTO : dtoList) {
            set.add(toEntity(fileDTO, parent));
        }

        return set;
    }

    @Override
    public Set<FileDTO> toDto(Set<FileEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<FileDTO> set = new HashSet<FileDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (FileEntity fileEntity : entityList) {
            set.add(toDto(fileEntity));
        }

        return set;
    }

    protected Set<FileDTO> toDto(Set<FileEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<FileDTO> set = new HashSet<FileDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (FileEntity fileEntity : entityList) {
            set.add(toDto(fileEntity, parent));
        }

        return set;
    }

}
