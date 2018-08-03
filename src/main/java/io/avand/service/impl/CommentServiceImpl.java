package io.avand.service.impl;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CommentEntity;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.repository.jpa.CommentRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CommentService;
import io.avand.service.dto.CommentDTO;
import io.avand.service.mapper.CommentMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final CandidateRepository candidateRepository;
    private final CommentMapper commentMapper;
    private final SecurityUtils securityUtils;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CandidateRepository candidateRepository,
                              CommentMapper commentMapper,
                              SecurityUtils securityUtils) {
        this.commentRepository = commentRepository;
        this.candidateRepository = candidateRepository;
        this.commentMapper = commentMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) throws NotFoundException {
        log.debug("Request to save comment : {}", commentDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(commentDTO.getCandidateId());
        if (candidateEntity != null) {
            CommentEntity commentEntity = commentMapper.toEntity(commentDTO);
            commentEntity.setCandidate(candidateEntity);
            commentEntity.setUserId(securityUtils.getCurrentUserId());
            commentEntity = commentRepository.save(commentEntity);
            return commentMapper.toDto(commentEntity);
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public CommentDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find comment by id : {}", id);
        CommentEntity commentEntity = commentRepository.findOne(id);
        if (commentEntity != null) {
            return commentMapper.toDto(commentEntity);
        } else {
            throw new NotFoundException("Comment Not Found");
        }
    }

    @Override
    public Page<CommentDTO> findAll(Pageable pageable) {
        log.debug("Request to find all comment");
        return commentRepository.findAll(pageable)
            .map(commentMapper::toDto);
    }

    @Override
    public Page<CommentDTO> findAllByCandidateId(Pageable pageable, Long id) {
        log.debug("Request to find all comment by candidate id");
        return commentRepository.findAllByCandidate_Id(pageable, id)
            .map(commentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete comment by id : {}", id);
        commentRepository.delete(id);
    }
}
