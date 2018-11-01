package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.aop.event.CustomEvent;
import io.avand.domain.enumeration.EventType;
import io.avand.service.CandidateMessageService;
import io.avand.service.dto.CandidateMessageDTO;
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

    public MailResource(CandidateMessageService candidateMessageService,
                        ApplicationEventPublisher eventPublisher) {
        this.candidateMessageService = candidateMessageService;
        this.eventPublisher = eventPublisher;
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
                newMessage.setFromUserId(originalMessage.getToUserId());
                newMessage.setToUserId(originalMessage.getFromUserId());
                newMessage.setCandidateId(originalMessage.getCandidateId());
                newMessage.setParentId(originalMessage.getId());
                newMessage.setSubject(mail.get("Subject") == null ? "" : this.getValue(mail, "Subject"));
                newMessage.setMessage(mail.get("body-plain") == null ? "" : this.getValue(mail, "body-plain"));
                newMessage.setMessageId(this.getValue(mail, "Message-Id").replace("[", "").replace("]", ""));
                newMessage = candidateMessageService.saveInReply(newMessage);

                CustomEvent customEvent = new CustomEvent(this);
                customEvent.setTitle(newMessage.getSubject());
                customEvent.setDescription("New Email From : " + newMessage.getFromUserId() + " To : " + newMessage.getToUserId());
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
