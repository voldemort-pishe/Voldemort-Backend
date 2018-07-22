package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateMessageEntity;
import io.avand.service.dto.CandidateMessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CandidateMessageMapper extends EntityMapper<CandidateMessageDTO, CandidateMessageEntity> {

    @Override
    @Mapping(source = "candidate.id",target = "candidateId")
    @Mapping(source = "parent.id",target = "parentId")
    CandidateMessageDTO toDto(CandidateMessageEntity entity);
}
