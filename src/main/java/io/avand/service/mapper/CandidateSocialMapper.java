package io.avand.service.mapper;

import io.avand.domain.entity.jpa.CandidateSocialEntity;
import io.avand.service.dto.CandidateSocialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateSocialMapper extends EntityMapper<CandidateSocialDTO, CandidateSocialEntity> {

    @Override
    @Mapping(source = "candidate.id", target = "candidateId")
    CandidateSocialDTO toDto(CandidateSocialEntity entity);
}
