package io.avand.service;

import io.avand.service.dto.CandidateMessageDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CandidateMessageService {

    CandidateMessageDTO save(CandidateMessageDTO candidateMessageDTO) throws NotFoundException;

    CandidateMessageDTO findById(Long id) throws NotFoundException;

    Page<CandidateMessageDTO> findByCandidateId(Long candidateId, Pageable pageable);

    void delete(Long id) throws NotFoundException;
}
