package io.avand.service.impl;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.FeedbackEntity;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.FeedbackRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.FeedbackService;
import io.avand.service.dto.FeedbackDTO;
import io.avand.service.mapper.FeedbackMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    private final FeedbackRepository feedbackRepository;
    private final CandidateRepository candidateRepository;
    private final FeedbackMapper feedbackMapper;
    private final SecurityUtils securityUtils;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               CandidateRepository candidateRepository,
                               FeedbackMapper feedbackMapper,
                               SecurityUtils securityUtils) {
        this.feedbackRepository = feedbackRepository;
        this.candidateRepository = candidateRepository;
        this.feedbackMapper = feedbackMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public FeedbackDTO save(FeedbackDTO feedbackDTO) throws NotFoundException {
        log.debug("Request to save feedback : {}", feedbackDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(feedbackDTO.getCandidateId());
        if (candidateEntity != null) {
            FeedbackEntity feedbackEntity = feedbackMapper.toEntity(feedbackDTO);
            feedbackEntity.setUserId(securityUtils.getCurrentUserId());
            feedbackEntity.setCandidate(candidateEntity);
            feedbackEntity = feedbackRepository.save(feedbackEntity);
            return feedbackMapper.toDto(feedbackEntity);
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public FeedbackDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find feedback by id : {}", id);
        FeedbackEntity feedbackEntity = feedbackRepository.findOne(id);
        if (feedbackEntity != null) {
            return feedbackMapper.toDto(feedbackEntity);
        } else {
            throw new NotFoundException("Feedback Not Found");
        }
    }

    @Override
    public List<FeedbackDTO> findAll() {
        log.debug("Request to find all feedback");
        return feedbackRepository.findAll()
            .stream()
            .map(feedbackMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete feedback by id : {}", id);
        feedbackRepository.delete(id);
    }
}
