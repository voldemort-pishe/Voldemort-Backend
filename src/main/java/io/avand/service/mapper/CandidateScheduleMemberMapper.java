package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateScheduleMemberEntity;
import io.avand.service.dto.CandidateScheduleMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateScheduleMemberMapper
    extends EntityMapper<CandidateScheduleMemberDTO, CandidateScheduleMemberEntity> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "candidateSchedule.id", target = "candidateScheduleId")
    CandidateScheduleMemberDTO toDto(CandidateScheduleMemberEntity entity);
}
