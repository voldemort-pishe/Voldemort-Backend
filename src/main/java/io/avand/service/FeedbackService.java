package io.avand.service;

import io.avand.service.dto.FeedbackDTO;
import javassist.NotFoundException;

import java.util.List;

public interface FeedbackService {

    FeedbackDTO save(FeedbackDTO feedbackDTO) throws NotFoundException;

    FeedbackDTO update(FeedbackDTO feedbackDTO) throws NotFoundException;

    FeedbackDTO findById(Long id) throws NotFoundException;

    List<FeedbackDTO> findAll();

    void delete(Long id);

}
