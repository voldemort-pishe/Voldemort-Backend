package io.avand.service.mapper;

import io.avand.domain.entity.jpa.FileEntity;
import io.avand.service.dto.FileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface FileMapper extends EntityMapper<FileDTO, FileEntity> {
}
