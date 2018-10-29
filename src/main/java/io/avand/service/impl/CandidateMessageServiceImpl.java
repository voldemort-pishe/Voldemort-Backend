package io.avand.service.impl;

import io.avand.domain.entity.jpa.CandidateEntity;
import io.avand.domain.entity.jpa.CandidateMessageEntity;
import io.avand.mailgun.service.MailGunMessageService;
import io.avand.mailgun.service.dto.request.MailGunSendMessageRequestDTO;
import io.avand.mailgun.service.dto.response.MailGunSendMessageResponseDTO;
import io.avand.mailgun.service.error.MailGunException;
import io.avand.repository.jpa.CandidateMessageRepository;
import io.avand.repository.jpa.CandidateRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CandidateMessageService;
import io.avand.service.UserService;
import io.avand.service.dto.CandidateMessageDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.CandidateMessageMapper;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateMessageServiceImpl implements CandidateMessageService {

    private final Logger log = LoggerFactory.getLogger(CandidateMessageServiceImpl.class);
    private final CandidateMessageRepository candidateMessageRepository;
    private final CandidateMessageMapper candidateMessageMapper;
    private final CandidateRepository candidateRepository;
    private final SecurityUtils securityUtils;
    private final MailGunMessageService mailGunMessageService;
    private final UserService userService;

    public CandidateMessageServiceImpl(CandidateMessageRepository candidateMessageRepository,
                                       CandidateMessageMapper candidateMessageMapper,
                                       CandidateRepository candidateRepository,
                                       SecurityUtils securityUtils,
                                       MailGunMessageService mailGunMessageService,
                                       UserService userService) {
        this.candidateMessageRepository = candidateMessageRepository;
        this.candidateMessageMapper = candidateMessageMapper;
        this.candidateRepository = candidateRepository;
        this.securityUtils = securityUtils;
        this.mailGunMessageService = mailGunMessageService;
        this.userService = userService;
    }


    @Override
    public CandidateMessageDTO save(CandidateMessageDTO candidateMessageDTO) throws NotFoundException {
        log.debug("Request to save candidateMessage : {}", candidateMessageDTO);
        CandidateEntity candidateEntity = candidateRepository.findOne(candidateMessageDTO.getCandidateId());
        if (candidateEntity != null) {
            CandidateMessageEntity parentMessage = null;

            if (candidateMessageDTO.getParentId() != null) {
                parentMessage = candidateMessageRepository.findOne(candidateMessageDTO.getParentId());
                if (parentMessage == null) {
                    throw new NotFoundException("Parent Message Not Found");
                }
            }

            Optional<UserDTO> userDTO = userService.findById(candidateMessageDTO.getToUserId());
            if (userDTO.isPresent()) {

                MailGunSendMessageRequestDTO mailGunSendMessageRequestDTO = new MailGunSendMessageRequestDTO();
                mailGunSendMessageRequestDTO.setSubject(candidateMessageDTO.getSubject());
                mailGunSendMessageRequestDTO.setText(candidateMessageDTO.getMessage());
                mailGunSendMessageRequestDTO.setTo(userDTO.get().getEmail());
                try {
                    MailGunSendMessageResponseDTO mailGunSendMessageResponseDTO =
                        mailGunMessageService.sendMessage(mailGunSendMessageRequestDTO);

                    CandidateMessageEntity candidateMessageEntity = candidateMessageMapper.toEntity(candidateMessageDTO);
                    candidateMessageEntity.setFromUserId(securityUtils.getCurrentUserId());
                    candidateMessageEntity.setCandidate(candidateEntity);
                    candidateMessageEntity.setParent(parentMessage);
                    candidateMessageEntity.setMessageId(mailGunSendMessageResponseDTO.getId());
                    candidateMessageEntity = candidateMessageRepository.save(candidateMessageEntity);
                    return candidateMessageMapper.toDto(candidateMessageEntity);
                } catch (MailGunException e) {
                    throw new ServerErrorException(e.getMessage());
                }
            } else {
                throw new NotFoundException("User Not Found To Send Email");
            }
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public CandidateMessageDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find candidate Message by id : {}", id);
        CandidateMessageEntity candidateMessageEntity = candidateMessageRepository.findOne(id);
        if (candidateMessageEntity != null) {
            return candidateMessageMapper.toDto(candidateMessageEntity);
        } else {
            throw new NotFoundException("Candidate Message Not Available");
        }
    }

    @Override
    public Page<CandidateMessageDTO> findByCandidateId(Long candidateId, Pageable pageable) {
        log.debug("Request to find all candidate message by candidate id : {}", candidateId);
        return candidateMessageRepository.findAllByCandidate_Id(candidateId, pageable)
            .map(candidateMessageMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete candidate message by id : {}", id);
        CandidateMessageEntity candidateMessageEntity = candidateMessageRepository.findOne(id);
        if (candidateMessageEntity != null) {
            candidateMessageRepository.delete(candidateMessageEntity);
        } else {
            throw new NotFoundException("Candidate Message Not Available");
        }
    }
}
