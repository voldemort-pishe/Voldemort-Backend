package io.avand.service;

import io.avand.domain.entity.jpa.UserEntity;
import io.avand.mailgun.service.MailGunMessageService;
import io.avand.mailgun.service.dto.request.MailGunSendMessageRequestDTO;
import io.avand.mailgun.service.error.MailGunException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    private final Locale locale = Locale.forLanguageTag("fa");

    private final SpringTemplateEngine templateEngine;
    private final MailGunMessageService mailGunMessageService;

    public MailService(SpringTemplateEngine templateEngine,
                       MailGunMessageService mailGunMessageService) {
        this.templateEngine = templateEngine;
        this.mailGunMessageService = mailGunMessageService;
    }


    @Async
    public void sendEmail(MailGunSendMessageRequestDTO requestDTO) throws MailGunException {
        log.debug("Request to send email : {}", requestDTO);

        mailGunMessageService.sendMessage(requestDTO);
    }

    @Async
    public void sendActivationEmail(UserEntity user) throws MailGunException {
        log.debug("Sending activation email to '{}'", user.getEmail());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        String content = templateEngine.process("activationEmail", context);
        MailGunSendMessageRequestDTO sendMessageRequestDTO = new MailGunSendMessageRequestDTO();
        sendMessageRequestDTO.setTo(user.getEmail());
        sendMessageRequestDTO.setFromName("شرکت آوند");
        sendMessageRequestDTO.setSubject("فعال سازی حساب کاربری");
        sendMessageRequestDTO.setText(content);
        sendMessageRequestDTO.setHtml(content);
        this.sendEmail(sendMessageRequestDTO);
    }

    @Async
    public void sendPasswordResetMail(UserEntity user) throws MailGunException {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        String content = templateEngine.process("passwordResetEmail", context);
        MailGunSendMessageRequestDTO sendMessageRequestDTO = new MailGunSendMessageRequestDTO();
        sendMessageRequestDTO.setTo(user.getEmail());
        sendMessageRequestDTO.setFromName("شرکت آوند");
        sendMessageRequestDTO.setSubject("فراموشی کلمه عبور");
        sendMessageRequestDTO.setText(content);
        sendMessageRequestDTO.setHtml(content);
        this.sendEmail(sendMessageRequestDTO);
    }

    @Async
    public void sendInviationEmail(UserEntity user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "invitationEmail", "email.activation.title");
    }

    @Async
    public void sendInviationMemberEmail(UserEntity user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "invitationMemberEmail", "email.activation.title");
    }

    @Async
    public void sendInviationMemberEmailWithRegister(UserEntity user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "invitationMemberEmailWithRegister", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(UserEntity user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "creationEmail", "email.activation.title");
    }

}
