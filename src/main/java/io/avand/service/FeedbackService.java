package io.avand.service;

import io.avand.service.dto.FeedbackDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {

    FeedbackDTO save(FeedbackDTO feedbackDTO) throws NotFoundException;

    FeedbackDTO update(FeedbackDTO feedbackDTO) throws NotFoundException;

    FeedbackDTO findById(Long id) throws NotFoundException;

    Page<FeedbackDTO> findAll(Pageable pageable);

    void delete(Long id);

}
