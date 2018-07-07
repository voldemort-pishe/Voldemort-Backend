package io.avand.service;

import io.avand.service.dto.CandidateDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateService {

    CandidateDTO save(CandidateDTO candidateDTO) throws NotFoundException;

    CandidateDTO findById(Long id) throws NotFoundException;

    List<CandidateDTO> findAll();

    void delete(Long id);

}
