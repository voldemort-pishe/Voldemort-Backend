package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateScheduleEntity;
import io.avand.service.dto.CandidateScheduleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface CandidateScheduleMapper extends EntityMapper<CandidateScheduleDTO, CandidateScheduleEntity> {
}
