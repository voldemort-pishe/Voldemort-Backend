package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CommentEntity;
import hr.pishe.service.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, CommentEntity> {

    @Override
    @Mapping(source = "candidate.id",target = "candidateId")
    CommentDTO toDto(CommentEntity entity);
}
