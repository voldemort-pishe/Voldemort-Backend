package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateMessageEntity;
import io.avand.service.dto.CandidateMessageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CandidateMessageMapper extends EntityMapper<CandidateMessageDTO, CandidateMessageEntity> {
}
