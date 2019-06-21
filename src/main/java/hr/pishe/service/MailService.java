package hr.pishe.service;

import hr.pishe.config.ApplicationProperties;
import hr.pishe.domain.entity.jpa.CandidateScheduleEntity;
import hr.pishe.domain.entity.jpa.CompanyMemberEntity;
import hr.pishe.domain.entity.jpa.UserEntity;
import hr.pishe.mailgun.service.MailGunMessageService;
import hr.pishe.mailgun.service.dto.request.MailGunSendMessageRequestDTO;
import hr.pishe.mailgun.service.error.MailGunException;
import hr.pishe.service.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.io.File;
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
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String COMPANY_MEMBER = "companyMember";
    private static final String CANDIDATE_SCHEDULE = "candidateSchedule";
    private static final String PANEL_URL = "panelUrl";
    private static final String REGISTER_EMAIL_URL = "registerEmailUrl";
    private final Locale locale = Locale.forLanguageTag("fa");

    private final SpringTemplateEngine templateEngine;
    private final MailGunMessageService mailGunMessageService;
    private final ApplicationProperties applicationProperties;

    public MailService(MailGunMessageService mailGunMessageService,
                       SpringTemplateEngine templateEngine, ApplicationProperties applicationProperties) {
        this.mailGunMessageService = mailGunMessageService;
        this.templateEngine = templateEngine;
        this.applicationProperties = applicationProperties;
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
        context.setVariable(REGISTER_EMAIL_URL,
            applicationProperties.getBase().getPanel() + applicationProperties.getBase().getRegisterEmailUrl());
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
    public void sendScheduleCandidate(CandidateScheduleEntity candidateScheduleEntity, String name, String email, File attachment) {
        log.debug("Sending Schedule email to : {}", candidateScheduleEntity);
        Context context = new Context(locale);
        context.setVariable(CANDIDATE_SCHEDULE, candidateScheduleEntity);
        context.setVariable(DATE, DateUtil.gToPersianWithTime(DateUtil.toDate(candidateScheduleEntity.getStartDate())));
        context.setVariable(NAME, name);
        String content = templateEngine.process("scheduleCandidateEmail", context);
        MailGunSendMessageRequestDTO sendMessageRequestDTO = new MailGunSendMessageRequestDTO();
        sendMessageRequestDTO.setTo(email);
        sendMessageRequestDTO.setFromName(candidateScheduleEntity.getCandidate().getJob().getCompany().getNameFa());
        sendMessageRequestDTO.setSubject("جلسه مصاحبه");
        sendMessageRequestDTO.setText(content);
        sendMessageRequestDTO.setHtml(content);
        sendMessageRequestDTO.setAttachment(attachment);
        this.sendEmail(sendMessageRequestDTO);
    }

    @Async
    public void sendScheduleTeam(CandidateScheduleEntity candidateScheduleEntity, String name, String email, File attachment) {
        log.debug("Sending Schedule email to : {}", candidateScheduleEntity);
        Context context = new Context(locale);
        context.setVariable(CANDIDATE_SCHEDULE, candidateScheduleEntity);
        context.setVariable(DATE, DateUtil.gToPersianWithTime(DateUtil.toDate(candidateScheduleEntity.getStartDate())));
        context.setVariable(NAME, name);
        String content = templateEngine.process("scheduleTeamEmail", context);
        MailGunSendMessageRequestDTO sendMessageRequestDTO = new MailGunSendMessageRequestDTO();
        sendMessageRequestDTO.setTo(email);
        sendMessageRequestDTO.setFromName(candidateScheduleEntity.getCandidate().getJob().getCompany().getNameFa());
        sendMessageRequestDTO.setSubject("جلسه مصاحبه");
        sendMessageRequestDTO.setText(content);
        sendMessageRequestDTO.setHtml(content);
        sendMessageRequestDTO.setAttachment(attachment);
        this.sendEmail(sendMessageRequestDTO);
    }

}
