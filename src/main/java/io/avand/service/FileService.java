package io.avand.service;

import io.avand.service.dto.FileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FileService {

    FileDTO save(FileDTO fileDTO);

    Optional<FileDTO> findById(Long id);

    Page<FileDTO> findAll(Pageable pageable);

    void delete(Long id);

}
