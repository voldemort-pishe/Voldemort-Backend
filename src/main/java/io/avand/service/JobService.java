package io.avand.service;

import io.avand.service.dto.JobDTO;
import javassist.NotFoundException;

import java.util.List;

public interface JobService {

    JobDTO save(JobDTO jobDTO) throws NotFoundException;

    JobDTO findById(Long id) throws NotFoundException;

    List<JobDTO> findAll() throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
