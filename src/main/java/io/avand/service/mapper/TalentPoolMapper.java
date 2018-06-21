package io.avand.service.mapper;

import io.avand.domain.TalentPoolEntity;
import io.avand.service.dto.TalentPoolDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TalentPoolMapper extends EntityMapper<TalentPoolDTO, TalentPoolEntity> {
}
