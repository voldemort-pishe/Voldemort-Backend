package io.avand.service.mapper;

import io.avand.domain.FileEntity;
import io.avand.service.dto.FileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, FileEntity> {
}
