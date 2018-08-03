package io.avand.service;

import io.avand.service.dto.CommentDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CommentDTO save(CommentDTO commentDTO) throws NotFoundException;

    CommentDTO findById(Long id) throws NotFoundException;

    Page<CommentDTO> findAll(Pageable pageable);

    Page<CommentDTO> findAllByCandidateId(Pageable pageable, Long id);

    void delete(Long id);

}
