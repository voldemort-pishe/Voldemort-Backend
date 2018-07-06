package io.avand.service.mapper;

import io.avand.domain.TalentPoolEntity;
import io.avand.domain.UserEntity;
import io.avand.service.dto.TalentPoolDTO;
import io.avand.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {})
public interface TalentPoolMapper extends EntityMapper<TalentPoolDTO, TalentPoolEntity> {
}
