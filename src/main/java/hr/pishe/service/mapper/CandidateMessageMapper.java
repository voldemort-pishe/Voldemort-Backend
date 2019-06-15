package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CandidateMessageEntity;
import hr.pishe.service.dto.CandidateMessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CandidateMessageMapper extends EntityMapper<CandidateMessageDTO, CandidateMessageEntity> {

    @Override
    @Mapping(source = "candidate.id",target = "candidateId")
    @Mapping(source = "parent.id",target = "parentId")
    CandidateMessageDTO toDto(CandidateMessageEntity entity);
}
