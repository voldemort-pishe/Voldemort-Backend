package hr.pishe.service.impl;

import hr.pishe.config.ApplicationProperties;
import hr.pishe.domain.entity.jpa.CandidateEntity;
import hr.pishe.domain.entity.jpa.CandidateMessageEntity;
import hr.pishe.domain.enumeration.CandidateMessageOwnerType;
import hr.pishe.mailgun.service.MailGunMessageService;
import hr.pishe.mailgun.service.dto.request.MailGunSendMessageRequestDTO;
import hr.pishe.mailgun.service.dto.response.MailGunSendMessageResponseDTO;
import hr.pishe.mailgun.service.error.MailGunException;
import hr.pishe.repository.jpa.CandidateMessageRepository;
import hr.pishe.repository.jpa.CandidateRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.CandidateMessageService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.CandidateMessageDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.CandidateMessageMapper;
import hr.pishe.web.rest.errors.ServerErrorException;
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
    private final ApplicationProperties applicationProperties;

    public CandidateMessageServiceImpl(CandidateMessageRepository candidateMessageRepository,
                                       CandidateMessageMapper candidateMessageMapper,
                                       CandidateRepository candidateRepository,
                                       SecurityUtils securityUtils,
                                       MailGunMessageService mailGunMessageService,
                                       UserService userService,
                                       ApplicationProperties applicationProperties) {
        this.candidateMessageRepository = candidateMessageRepository;
        this.candidateMessageMapper = candidateMessageMapper;
        this.candidateRepository = candidateRepository;
        this.securityUtils = securityUtils;
        this.mailGunMessageService = mailGunMessageService;
        this.userService = userService;
        this.applicationProperties = applicationProperties;
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
            String toEmail;
            if (candidateMessageDTO.getOwner() == CandidateMessageOwnerType.CANDIDATE) {
                Optional<UserDTO> userDTO = userService.findById(candidateMessageDTO.getToUserId());
                if (userDTO.isPresent()) {
                    toEmail = userDTO.get().getEmail();
                } else {
                    throw new NotFoundException("User NotFound");
                }
            } else {
                toEmail = candidateEntity.getEmail();
            }
            MailGunSendMessageRequestDTO mailGunSendMessageRequestDTO = new MailGunSendMessageRequestDTO();
            mailGunSendMessageRequestDTO.setSubject(candidateMessageDTO.getSubject());
            mailGunSendMessageRequestDTO.setFromName(candidateEntity.getJob().getCompany().getNameFa());
            mailGunSendMessageRequestDTO.setText(candidateMessageDTO.getMessage());
            mailGunSendMessageRequestDTO.setTo(toEmail);
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
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public CandidateMessageDTO save(String subject, String message, Long parent, Long candidateId) throws NotFoundException {
        log.debug("Request to save candidateMessage : {}, {}, {}, {}", subject, message, parent, candidateId);
        CandidateEntity candidateEntity = candidateRepository.findOne(candidateId);
        if (candidateEntity != null) {
            CandidateMessageEntity parentMessage = null;

            if (parent != null) {
                parentMessage = candidateMessageRepository.findOne(parent);
                if (parentMessage == null) {
                    throw new NotFoundException("Parent Message Not Found");
                }
            }

            MailGunSendMessageRequestDTO mailGunSendMessageRequestDTO = new MailGunSendMessageRequestDTO();
            mailGunSendMessageRequestDTO.setSubject(subject);
            mailGunSendMessageRequestDTO.setFromName(candidateEntity.getJob().getCompany().getNameFa());
            mailGunSendMessageRequestDTO.setText(message);
            mailGunSendMessageRequestDTO.setTo(candidateEntity.getEmail());
            try {
                MailGunSendMessageResponseDTO mailGunSendMessageResponseDTO =
                    mailGunMessageService.sendMessage(mailGunSendMessageRequestDTO);

                CandidateMessageEntity candidateMessageEntity = new CandidateMessageEntity();
                candidateMessageEntity.setFromUserId(securityUtils.getCurrentUserId());
                candidateMessageEntity.setToUserId(candidateId);
                candidateMessageEntity.setOwner(CandidateMessageOwnerType.USER);
                candidateMessageEntity.setSubject(subject);
                candidateMessageEntity.setMessage(message);
                candidateMessageEntity.setCandidate(candidateEntity);
                candidateMessageEntity.setParent(parentMessage);
                candidateMessageEntity.setMessageId(mailGunSendMessageResponseDTO.getId());
                candidateMessageEntity = candidateMessageRepository.save(candidateMessageEntity);
                return candidateMessageMapper.toDto(candidateMessageEntity);

            } catch (MailGunException e) {
                throw new ServerErrorException(e.getMessage());
            }
        } else {
            throw new NotFoundException("Candidate Not Found");
        }
    }

    @Override
    public CandidateMessageDTO saveInReply(CandidateMessageDTO candidateMessageDTO) throws NotFoundException {
        log.debug("Request to saveInReply candidateMessage : {}", candidateMessageDTO);

        CandidateEntity candidateEntity = candidateRepository.findOne(candidateMessageDTO.getCandidateId());

        if (candidateEntity != null) {
            CandidateMessageEntity parentMessage = null;

            if (candidateMessageDTO.getParentId() != null) {
                parentMessage = candidateMessageRepository.findOne(candidateMessageDTO.getParentId());
                if (parentMessage == null) {
                    throw new NotFoundException("Parent Message Not Found");
                }
            }

            CandidateMessageEntity candidateMessageEntity = candidateMessageMapper.toEntity(candidateMessageDTO);
            candidateMessageEntity.setCandidate(candidateEntity);
            candidateMessageEntity.setParent(parentMessage);
            candidateMessageEntity = candidateMessageRepository.save(candidateMessageEntity);
            return candidateMessageMapper.toDto(candidateMessageEntity);

        } else {
            throw new NotFoundException("Candidate Not Found");
        }

    }

    @Override
    public CandidateMessageDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find candidate Message by id : {}", id);
        CandidateMessageEntity candidateMessageEntity =
            candidateMessageRepository.findByIdAndCandidate_Job_Company_Id(id, securityUtils.getCurrentCompanyId());
        if (candidateMessageEntity != null) {
            return candidateMessageMapper.toDto(candidateMessageEntity);
        } else {
            throw new NotFoundException("Candidate Message Not Available");
        }
    }

    @Override
    public Page<CandidateMessageDTO> findByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException {
        log.debug("Request to find all candidate message by candidate id : {}", candidateId);
        return candidateMessageRepository
            .findAllByCandidate_IdAndCandidate_Job_Company_Id(candidateId, securityUtils.getCurrentCompanyId(), pageable)
            .map(candidateMessageMapper::toDto);
    }

    @Override
    public Optional<CandidateMessageDTO> findByMessageId(String messageId) {
        log.debug("Request to find candidate message by messageId : {}", messageId);
        return candidateMessageRepository
            .findByMessageId(messageId)
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
