package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.aop.event.CustomEvent;
import io.avand.domain.enumeration.CandidateMessageOwnerType;
import io.avand.domain.enumeration.EventType;
import io.avand.service.CandidateMessageService;
import io.avand.service.CandidateService;
import io.avand.service.UserService;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CandidateMessageDTO;
import io.avand.service.dto.UserDTO;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/api/mail")
public class MailResource {

    private final Logger log = LoggerFactory.getLogger(MailResource.class);
    private final CandidateMessageService candidateMessageService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final CandidateService candidateService;

    public MailResource(CandidateMessageService candidateMessageService,
                        ApplicationEventPublisher eventPublisher,
                        UserService userService,
                        CandidateService candidateService) {
        this.candidateMessageService = candidateMessageService;
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.candidateService = candidateService;
    }

    @PostMapping("/income")
    @Timed
    public ResponseEntity incomeMail(@RequestBody MultiValueMap mail) throws NotFoundException {

        log.info("-----------------------------");
        log.info("From : {}", mail.get("from"));
        log.info("To : {}", mail.get("To"));
        log.info("Subject : {}", mail.get("Subject"));
        log.info("BodyPlain : {}", mail.get("body-plain"));
        log.info("HTML : {}", mail.get("stripped-html"));
        log.info("InReplyTo : {}", mail.get("In-Reply-To"));
        log.info("MessageID : {}", mail.get("Message-Id"));
        log.info("Date : {}", mail.get("Date"));
        log.info("-----------------------------");

        String messageId = this.getValue(mail, "In-Reply-To");
        if (messageId != null) {
            messageId = messageId.replace("[", "").replace("]", "");
            Optional<CandidateMessageDTO> candidateMessageDTO = candidateMessageService.findByMessageId(messageId);
            if (candidateMessageDTO.isPresent()) {
                CandidateMessageDTO originalMessage = candidateMessageDTO.get();
                CandidateMessageDTO newMessage = new CandidateMessageDTO();
                newMessage.setOwner(CandidateMessageOwnerType.CANDIDATE);
                newMessage.setFromUserId(originalMessage.getToUserId());
                newMessage.setToUserId(originalMessage.getFromUserId());
                newMessage.setCandidateId(originalMessage.getCandidateId());
                newMessage.setParentId(originalMessage.getId());
                newMessage.setSubject(mail.get("Subject") == null ? "" : this.getValue(mail, "Subject"));
                newMessage.setMessage(mail.get("body-plain") == null ? "" : this.getValue(mail, "body-plain"));
                newMessage.setMessageId(this.getValue(mail, "Message-Id").replace("[", "").replace("]", ""));
                newMessage = candidateMessageService.saveInReply(newMessage);

                CandidateDTO candidateDTO = candidateService.findById(newMessage.getFromUserId());

//                Optional<UserDTO> userDTO = userService.findById(newMessage.getFromUserId());

                String name = candidateDTO != null ? candidateDTO.getFirstName() + " " + candidateDTO.getLastName() : "ناشناس";
                ;
                CustomEvent customEvent = new CustomEvent(this);
                customEvent.setTitle(name);
                customEvent.setDescription(String.format("ایمیل از %s", name));
                customEvent.setType(EventType.EMAIL);
                customEvent.setExtra(newMessage.getId().toString());
                customEvent.setOwner(newMessage.getToUserId());
                eventPublisher.publishEvent(customEvent);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public String getValue(MultiValueMap mail, String key) {
        LinkedList<String> linkedList = (LinkedList<String>) mail.get(key);
        return linkedList.getFirst();
    }

}
