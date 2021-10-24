package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.configuration.MessageHelperConfiguration;
import com.luv2code.travelchecker.exception.PrepareEmailContentException;
import com.luv2code.travelchecker.exception.SendEmailException;
import com.luv2code.travelchecker.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    private final MessageHelperConfiguration messageHelperConfiguration;

    @Autowired
    public MailServiceImpl(final JavaMailSender javaMailSender,
                           final TemplateEngine templateEngine,
                           final MessageHelperConfiguration messageHelperConfiguration) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.messageHelperConfiguration = messageHelperConfiguration;
    }

    @Override
    public void sendPasswordResetRequest(final String firstName, final String email, final String resetToken) {
        LOGGER.info("Preparing to send email to: {}.", email);
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(messageHelperConfiguration.getFrom());
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(messageHelperConfiguration.getSubject());
            mimeMessage.setContent(prepareHtml(firstName, email, resetToken), MediaType.TEXT_HTML_VALUE);
        } catch (final MessagingException exception) {
            LOGGER.error("Error while preparing email content for User with email: {}.", email);
            throw new PrepareEmailContentException("Email content was not prepared right.");
        }

        send(email, mimeMessage);
    }

    private String prepareHtml(final String firstName, final String email, final String resetToken){
        LOGGER.info("Preparing HTML email template.");

        final Context context = new Context();
        context.setLocale(Locale.getDefault());
        context.setVariable("firstName", firstName);

        final String resetUrl = getResetUrl(resetToken);
        context.setVariable("resetUrl", resetUrl);
        return templateEngine.process("reset-password.html", context);
    }

    private String getResetUrl(final String resetToken) {
        return messageHelperConfiguration.getResetUrl()
                .replace("$resetToken", resetToken);
    }

    private void send(final String to, final MimeMessage mimeMessage) {
        try {
            javaMailSender.send(mimeMessage);
            LOGGER.info("Email successfully sent to: {}.", to);
        } catch (final MailException exception) {
            LOGGER.error("Error while trying to send email to: {}.", to);
            LOGGER.error(exception.getMessage());
            throw new SendEmailException("Email was not sent correctly.");
        }
    }
}
