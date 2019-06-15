package hr.pishe.service;

import hr.pishe.service.dto.FileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FileService {

    FileDTO save(FileDTO fileDTO);

    Optional<FileDTO> findById(Long id);

    Page<FileDTO> findAll(Pageable pageable);

    void delete(Long id);

}
