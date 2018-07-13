package io.avand.service.mapper;

import io.avand.domain.entity.jpa.TalentPoolEntity;
import io.avand.service.dto.TalentPoolDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface TalentPoolMapper extends EntityMapper<TalentPoolDTO, TalentPoolEntity> {
}
