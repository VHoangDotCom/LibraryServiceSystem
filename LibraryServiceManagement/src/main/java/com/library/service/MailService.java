package com.library.service;

import com.library.entity.email.MailRequest;
import com.library.entity.email.MailResponse;

import javax.mail.MessagingException;
import java.util.Map;

public interface MailService {

    void sendSimpleEmail(String toEmail, String body, String subject);

    void sendMailWithAttachment(String toEmail, String body, String subject, String attachment)
            throws MessagingException;

    void sendMailWithoutAttachment(String toEmail, String body, String subject)
            throws MessagingException;

    void sendMultipleMail(String toEmail, String body, String subject)
            throws MessagingException;

    MailResponse sendMailWithTemplate(MailRequest request, Map<String, Object> model);
    MailResponse sendMailRequestedResetPassword(MailRequest request, Map<String, Object> model);

    MailResponse sendMailCheckoutSuccess(MailRequest request, Map<String, Object> model);

    MailResponse sendMailCheckoutWhenBuyingSuccess(MailRequest request, Map<String, Object> model);

}

