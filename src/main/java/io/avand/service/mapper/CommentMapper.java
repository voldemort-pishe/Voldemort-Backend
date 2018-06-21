package io.avand.service.mapper;

import io.avand.domain.CommentEntity;
import io.avand.service.dto.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, CommentEntity> {
}
