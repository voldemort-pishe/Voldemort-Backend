package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CandidateScheduleMemberEntity;
import hr.pishe.service.dto.CandidateScheduleMemberDTO;
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
