package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CommentEntity;
import io.avand.service.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, CommentEntity> {

    @Override
    @Mapping(source = "candidate.id",target = "candidateId")
    CommentDTO toDto(CommentEntity entity);
}
