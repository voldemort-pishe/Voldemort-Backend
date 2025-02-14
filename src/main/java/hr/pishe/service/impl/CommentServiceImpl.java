package hr.pishe.service.impl;

import hr.pishe.aop.event.CustomEvent;
import hr.pishe.domain.entity.jpa.CandidateEntity;
import hr.pishe.domain.entity.jpa.CommentEntity;
import hr.pishe.domain.entity.jpa.JobHireTeamEntity;
import hr.pishe.domain.enumeration.EventType;
import hr.pishe.repository.jpa.CandidateRepository;
import hr.pishe.repository.jpa.CommentRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.CommentService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.CommentDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.CommentMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final CandidateRepository candidateRepository;
    private final CommentMapper commentMapper;
    private final SecurityUtils securityUtils;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CandidateRepository candidateRepository,
                              CommentMapper commentMapper,
                              SecurityUtils securityUtils,
                              UserService userService,
                              ApplicationEventPublisher eventPublisher) {
        this.commentRepository = commentRepository;
        this.candidateRepository = candidateRepository;
        this.commentMapper = commentMapper;
        this.securityUtils = securityUtils;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) throws NotFoundException {
        log.debug("Request to save comment : {}", commentDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(commentDTO.getCandidateId());
        if (candidateEntity != null) {

            Long userId = securityUtils.getCurrentUserId();

            CommentEntity commentEntity = commentMapper.toEntity(commentDTO);
            commentEntity.setCandidate(candidateEntity);
            commentEntity.setUserId(userId);
            commentEntity = commentRepository.save(commentEntity);

            Optional<UserDTO> userDTO = userService.findById(userId);
            String name = userDTO.map(userDTO1 -> userDTO1.getFirstName() + " " + userDTO1.getLastName()).orElse("ناشناس");
            for (JobHireTeamEntity jobHireTeamEntity : candidateEntity.getJob().getJobHireTeam()) {
                if (!jobHireTeamEntity.getUser().getId().equals(userId)) {
                    CustomEvent customEvent = new CustomEvent(this);
                    customEvent.setTitle(candidateEntity.getFirstName() + " " + candidateEntity.getLastName());
                    customEvent.setDescription(String.format("اظهار نظر از %s", name));
                    customEvent.setOwner(jobHireTeamEntity.getUser().getId());
                    customEvent.setType(EventType.COMMENT);
                    customEvent.setExtra(commentEntity.getId().toString());
                    eventPublisher.publishEvent(customEvent);
                }
            }

            return commentMapper.toDto(commentEntity);
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public CommentDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find comment by id : {}", id);
        CommentEntity commentEntity = commentRepository
            .findByIdAndCandidate_Job_Company_Id(id, securityUtils.getCurrentUserId());
        if (commentEntity != null) {
            return commentMapper.toDto(commentEntity);
        } else {
            throw new NotFoundException("Comment Not Found");
        }
    }

    @Override
    public Page<CommentDTO> findAllByCandidateId(Pageable pageable, Long candidateId) throws NotFoundException {
        log.debug("Request to find all comment by candidate id");
        return commentRepository
            .findAllByCandidate_IdAndCandidate_Job_Company_Id
                (pageable, candidateId, securityUtils.getCurrentCompanyId())
            .map(commentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete comment by id : {}", id);
        commentRepository.delete(id);
    }
}
