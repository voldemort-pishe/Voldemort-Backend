package io.avand.service;

import io.avand.service.dto.CommentDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CommentService {

    CommentDTO save(CommentDTO commentDTO) throws NotFoundException;

    CommentDTO findById(Long id) throws NotFoundException;

    List<CommentDTO> findAll();

    void delete(Long id);

}
