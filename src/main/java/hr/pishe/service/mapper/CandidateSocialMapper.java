package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CandidateSocialEntity;
import hr.pishe.service.dto.CandidateSocialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateSocialMapper extends EntityMapper<CandidateSocialDTO, CandidateSocialEntity> {

    @Override
    @Mapping(source = "candidate.id", target = "candidateId")
    CandidateSocialDTO toDto(CandidateSocialEntity entity);
}
