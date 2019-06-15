package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.FileEntity;
import hr.pishe.repository.jpa.FileRepository;
import hr.pishe.service.FileService;
import hr.pishe.service.dto.FileDTO;
import hr.pishe.service.mapper.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileServiceImpl(FileRepository fileRepository,
                           FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    public FileDTO save(FileDTO fileDTO) {
        log.debug("Request to save file : {}", fileDTO);
        FileEntity fileEntity = fileMapper.toEntity(fileDTO);
        fileEntity = fileRepository.save(fileEntity);
        return fileMapper.toDto(fileEntity);
    }

    @Override
    public Optional<FileDTO> findById(Long id) {
        log.debug("Request to find file by id : {}", id);
        return fileRepository
            .findById(id)
            .map(fileMapper::toDto);
    }

    @Override
    public Page<FileDTO> findAll(Pageable pageable) {
        log.debug("Request to find all files");
        return fileRepository.findAll(pageable)
            .map(fileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete file by id : {}", id);
        fileRepository.delete(id);
    }
}
