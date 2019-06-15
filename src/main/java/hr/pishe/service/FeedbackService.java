package hr.pishe.service;

import hr.pishe.service.dto.FeedbackDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    FeedbackDTO save(FeedbackDTO feedbackDTO) throws NotFoundException;

    FeedbackDTO update(FeedbackDTO feedbackDTO) throws NotFoundException;

    FeedbackDTO findById(Long id) throws NotFoundException;

    Page<FeedbackDTO> findAllByCandidateId(Pageable pageable, Long id) throws NotFoundException;

    void delete(Long id);

}
