package io.avand.service;

import io.avand.service.dto.FileDTO;

import java.util.List;

public interface FileService {

    FileDTO save(FileDTO fileDTO);

    FileDTO findById(Long id);

    List<FileDTO> findAll();

    void delete(Long id);

}
