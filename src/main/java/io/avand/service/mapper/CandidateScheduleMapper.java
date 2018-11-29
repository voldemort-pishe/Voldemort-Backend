package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.service.dto.CandidateScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CandidateScheduleMemberMapper.class})
public interface CandidateScheduleMapper extends EntityMapper<CandidateScheduleDTO, CandidateScheduleEntity> {

    @Override
    @Mapping(source = "candidate.id", target = "candidateId")
    CandidateScheduleDTO toDto(CandidateScheduleEntity entity);
}
