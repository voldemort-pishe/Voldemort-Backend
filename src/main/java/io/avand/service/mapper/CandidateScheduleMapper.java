package io.avand.service.mapper;

import io.avand.domain.CandidateScheduleEntity;
import io.avand.service.dto.CandidateScheduleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface CandidateScheduleMapper extends EntityMapper<CandidateScheduleDTO, CandidateScheduleEntity> {
}
