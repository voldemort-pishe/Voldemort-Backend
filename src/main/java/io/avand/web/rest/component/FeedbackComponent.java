package io.avand.web.rest.component;

import io.avand.service.dto.FeedbackDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackComponent {

    ResponseVM<FeedbackDTO> save(FeedbackDTO feedbackDTO) throws NotFoundException;

    ResponseVM<FeedbackDTO> update(FeedbackDTO feedbackDTO) throws NotFoundException;

    ResponseVM<FeedbackDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<FeedbackDTO>> findAllByCandidate(Long candidateId, Pageable pageable) throws NotFoundException;
}
