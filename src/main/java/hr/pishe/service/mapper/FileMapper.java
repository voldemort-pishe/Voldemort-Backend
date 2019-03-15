package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.FileEntity;
import hr.pishe.service.dto.FileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface FileMapper extends EntityMapper<FileDTO, FileEntity> {
}
