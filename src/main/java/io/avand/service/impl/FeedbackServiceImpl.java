package io.avand.service.impl;

import io.avand.aop.event.CustomEvent;
import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.FeedbackEntity;
import io.avand.domain.enumeration.EventType;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.FeedbackRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.FeedbackService;
import io.avand.service.UserService;
import io.avand.service.dto.FeedbackDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.FeedbackMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    private final FeedbackRepository feedbackRepository;
    private final CandidateRepository candidateRepository;
    private final FeedbackMapper feedbackMapper;
    private final SecurityUtils securityUtils;
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               CandidateRepository candidateRepository,
                               FeedbackMapper feedbackMapper,
                               SecurityUtils securityUtils,
                               ApplicationEventPublisher eventPublisher,
                               UserService userService) {
        this.feedbackRepository = feedbackRepository;
        this.candidateRepository = candidateRepository;
        this.feedbackMapper = feedbackMapper;
        this.securityUtils = securityUtils;
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @Override
    public FeedbackDTO save(FeedbackDTO feedbackDTO) throws NotFoundException {
        log.debug("Request to save feedback : {}", feedbackDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(feedbackDTO.getCandidateId());
        if (candidateEntity != null) {
            Long userId = securityUtils.getCurrentUserId();
            Optional<FeedbackEntity> previousFeedback =
                feedbackRepository.findByUserIdAndCandidate_Id(userId, candidateEntity.getId());
            if (!previousFeedback.isPresent()) {
                FeedbackEntity feedbackEntity = feedbackMapper.toEntity(feedbackDTO);
                feedbackEntity.setUserId(userId);
                feedbackEntity.setCandidate(candidateEntity);
                feedbackEntity = feedbackRepository.save(feedbackEntity);

                Optional<UserDTO> userDTO = userService.findById(userId);
                String name = userDTO.map(userDTO1 -> userDTO1.getFirstName() + " " + userDTO1.getLastName()).orElse("ناشناس");

                CustomEvent customEvent = new CustomEvent(this);
                customEvent.setTitle(candidateEntity.getFirstName() + " " + candidateEntity.getLastName());
                customEvent.setDescription(String.format("نظر از %s - %s", name, feedbackEntity.getFeedbackText()));
                customEvent.setExtra(feedbackEntity.getId().toString());
                customEvent.setOwner(userId);
                customEvent.setType(EventType.FEEDBACK);
                eventPublisher.publishEvent(customEvent);

                return feedbackMapper.toDto(feedbackEntity);
            } else {
                throw new IllegalStateException("شما قبلا برای این کارجو نظر داده‌اید");
            }
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public FeedbackDTO update(FeedbackDTO feedbackDTO) throws NotFoundException {
        log.debug("Request to update feedback : {}", feedbackDTO);
        FeedbackEntity feedbackEntity = feedbackRepository.findOne(feedbackDTO.getId());
        if (feedbackEntity != null) {
            if (feedbackEntity.getUserId().equals(securityUtils.getCurrentUserId())) {
                feedbackEntity.setFeedbackText(feedbackDTO.getFeedbackText());
                feedbackEntity.setRating(feedbackDTO.getRating());
                feedbackEntity = feedbackRepository.save(feedbackEntity);
                return feedbackMapper.toDto(feedbackEntity);
            } else {
                throw new SecurityException("You Don't have access to update this feedback");
            }
        } else {
            throw new NotFoundException("FeedBack Not Available");
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
    public Page<FeedbackDTO> findAll(Pageable pageable) {
        log.debug("Request to find all feedback");
        return feedbackRepository.findAll(pageable)
            .map(feedbackMapper::toDto);
    }

    @Override
    public Page<FeedbackDTO> findAllByCandidateId(Pageable pageable, Long id) {
        log.debug("Request to find all feedback by candidate id");
        return feedbackRepository.findAllByCandidate_Id(pageable, id)
            .map(feedbackMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete feedback by id : {}", id);
        feedbackRepository.delete(id);
    }
}
