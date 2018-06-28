package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.CommentEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommentMapper implements EntityMapper<CommentDTO, CommentEntity> {

    @Autowired
    private CandidateMapper candidateMapper;

    @Override
    public CommentEntity toEntity(CommentDTO dto) {
        if (dto == null) {
            return null;
        }

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setId(dto.getId());
        commentEntity.setUserId(dto.getUserId());
        commentEntity.setCommentText(dto.getCommentText());
        commentEntity.setStatus(dto.getStatus());
        commentEntity.setCandidate(candidateMapper.toEntity(dto.getCandidate()));

        return commentEntity;
    }

    protected CommentEntity toEntity(CommentDTO dto, CandidateEntity parent) {
        if (dto == null) {
            return null;
        }

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setId(dto.getId());
        commentEntity.setUserId(dto.getUserId());
        commentEntity.setCommentText(dto.getCommentText());
        commentEntity.setStatus(dto.getStatus());
        commentEntity.setCandidate(parent);

        return commentEntity;
    }

    @Override
    public CommentDTO toDto(CommentEntity entity) {
        if (entity == null) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(entity.getId());
        commentDTO.setUserId(entity.getUserId());
        commentDTO.setCommentText(entity.getCommentText());
        commentDTO.setStatus(entity.isStatus());
        commentDTO.setCandidate(candidateMapper.toDto(entity.getCandidate()));

        return commentDTO;
    }

    protected CommentDTO toDto(CommentEntity entity, CandidateDTO parent) {
        if (entity == null) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(entity.getId());
        commentDTO.setUserId(entity.getUserId());
        commentDTO.setCommentText(entity.getCommentText());
        commentDTO.setStatus(entity.isStatus());
        commentDTO.setCandidate(parent);

        return commentDTO;
    }

    @Override
    public List<CommentEntity> toEntity(List<CommentDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<CommentEntity> list = new ArrayList<CommentEntity>(dtoList.size());
        for (CommentDTO commentDTO : dtoList) {
            list.add(toEntity(commentDTO));
        }

        return list;
    }

    protected List<CommentEntity> toEntity(List<CommentDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<CommentEntity> list = new ArrayList<CommentEntity>(dtoList.size());
        for (CommentDTO commentDTO : dtoList) {
            list.add(toEntity(commentDTO, parent));
        }

        return list;
    }

    @Override
    public List<CommentDTO> toDto(List<CommentEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>(entityList.size());
        for (CommentEntity commentEntity : entityList) {
            list.add(toDto(commentEntity));
        }

        return list;
    }

    protected List<CommentDTO> toDto(List<CommentEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>(entityList.size());
        for (CommentEntity commentEntity : entityList) {
            list.add(toDto(commentEntity, parent));
        }

        return list;
    }

    @Override
    public Set<CommentEntity> toEntity(Set<CommentDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<CommentEntity> set = new HashSet<CommentEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CommentDTO commentDTO : dtoList) {
            set.add(toEntity(commentDTO));
        }

        return set;
    }

    protected Set<CommentEntity> toEntity(Set<CommentDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<CommentEntity> set = new HashSet<CommentEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CommentDTO commentDTO : dtoList) {
            set.add(toEntity(commentDTO, parent));
        }

        return set;
    }

    @Override
    public Set<CommentDTO> toDto(Set<CommentEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<CommentDTO> set = new HashSet<CommentDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CommentEntity commentEntity : entityList) {
            set.add(toDto(commentEntity));
        }

        return set;
    }

    protected Set<CommentDTO> toDto(Set<CommentEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<CommentDTO> set = new HashSet<CommentDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CommentEntity commentEntity : entityList) {
            set.add(toDto(commentEntity, parent));
        }

        return set;
    }

}
