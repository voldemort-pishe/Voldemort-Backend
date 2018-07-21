package io.avand.service;

import io.avand.service.dto.CandidateMessageDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateMessageService {

    CandidateMessageDTO save(CandidateMessageDTO candidateMessageDTO) throws NotFoundException;

    CandidateMessageDTO findById(Long id) throws NotFoundException;

    List<CandidateMessageDTO> findByCandidateId(Long candidateId);

    void delete(Long id) throws NotFoundException;
}
