package io.avand.service;

import io.avand.config.ApplicationProperties;
import io.avand.domain.entity.jpa.CompanyMemberEntity;
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
    private static final String COMPANY_MEMBER = "companyMember";
    private static final String PANEL_URL = "panelUrl";
    private final Locale locale = Locale.forLanguageTag("fa");
    private final ApplicationProperties applicationProperties;

    private final SpringTemplateEngine templateEngine;
    private final MailGunMessageService mailGunMessageService;

    public MailService(ApplicationProperties applicationProperties,
                       SpringTemplateEngine templateEngine,
                       MailGunMessageService mailGunMessageService) {
        this.applicationProperties = applicationProperties;
        this.templateEngine = templateEngine;
        this.mailGunMessageService = mailGunMessageService;
    }


    @Async
    public void sendEmail(MailGunSendMessageRequestDTO requestDTO) {
        log.debug("Request to send email : {}", requestDTO);
        try {
            mailGunMessageService.sendMessage(requestDTO);
        } catch (MailGunException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendActivationEmail(UserEntity user) {
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
    public void sendPasswordResetMail(UserEntity user) {
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
    public void sendInvitationMemberEmail(CompanyMemberEntity companyMemberEntity) {
        log.debug("Sending activation email to '{}'", companyMemberEntity);
        Context context = new Context(locale);
        context.setVariable(COMPANY_MEMBER, companyMemberEntity);
        String content = templateEngine.process("invitationMemberEmail", context);
        MailGunSendMessageRequestDTO sendMessageRequestDTO = new MailGunSendMessageRequestDTO();
        sendMessageRequestDTO.setTo(companyMemberEntity.getUser().getEmail());
        sendMessageRequestDTO.setFromName(companyMemberEntity.getCompany().getNameFa());
        sendMessageRequestDTO.setSubject("همکاران");
        sendMessageRequestDTO.setText(content);
        sendMessageRequestDTO.setHtml(content);
        this.sendEmail(sendMessageRequestDTO);
    }

    @Async
    public void sendInvitationMemberEmailWithRegister(CompanyMemberEntity companyMemberEntity) {
        log.debug("Sending activation email to '{}'", companyMemberEntity);
        Context context = new Context(locale);
        context.setVariable(COMPANY_MEMBER, companyMemberEntity);
        String content = templateEngine.process("invitationMemberEmailWithRegister", context);
        MailGunSendMessageRequestDTO sendMessageRequestDTO = new MailGunSendMessageRequestDTO();
        sendMessageRequestDTO.setTo(companyMemberEntity.getUser().getEmail());
        sendMessageRequestDTO.setFromName(companyMemberEntity.getCompany().getNameFa());
        sendMessageRequestDTO.setSubject("همکاران");
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
    public void sendCreationEmail(UserEntity user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "creationEmail", "email.activation.title");
    }

}
