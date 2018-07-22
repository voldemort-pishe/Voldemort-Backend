package io.avand.service.mapper;

import io.avand.domain.entity.jpa.TalentPoolEntity;
import io.avand.service.dto.TalentPoolDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface TalentPoolMapper extends EntityMapper<TalentPoolDTO, TalentPoolEntity> {

    @Override
    @Mapping(source = "user.id",target = "userId")
    TalentPoolDTO toDto(TalentPoolEntity entity);
}
