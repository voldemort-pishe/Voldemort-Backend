package io.avand.service.mapper;

import io.avand.domain.CompanyEntity;
import io.avand.domain.UserEntity;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {JobMapper.class,EvaluationCriteriaMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, CompanyEntity> {
}
