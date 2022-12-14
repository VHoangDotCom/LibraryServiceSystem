package com.library.service.impl;

import com.library.entity.User;
import com.library.entity.email.MailRequest;
import com.library.entity.email.MailResponse;
import com.library.repository.UserRepository;
import com.library.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private Configuration config;

    private final UserRepository userRepository;

    @Override
    public void sendSimpleEmail(String toEmail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("viethoang2001gun@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");
    }

    private static String[] Bcc_Mail = {
          /*  "hoangnvth2010033@fpt.edu.vn",
            "viethoang2001gun@gmail.com",
            "anhhvth2010043@fpt.edu.vn"*/
    };

   /* @Scheduled(cron = " 0 0/1 * * * *")//every 1 minute
    public void  triggerEveryMinute() throws MessagingException {
        //Working normally
        List<String> Bcc_mail = new ArrayList<String>();
        List<User> userList = userRepository.findAll();
        for(User user :userList){
            Bcc_mail.add(user.getEmail());
        }

        Bcc_Mail = Bcc_mail.toArray(new String[0]);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("viethoang2001gun@gmail.com");
        message.setTo("viethoang2001gun@gmail.com");
        message.setText("Hi there");
        message.setSubject("Xin chao");
        message.setBcc(Bcc_Mail);//send multiple mail with hide their identity

        mailSender.send(message);
        log.info("Every minute!!");
    }*/

    @Override
    public void sendMultipleMail(String toEmail, String body, String subject) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("viethoang2001gun@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        message.setBcc(Bcc_Mail);//send multiple mail with hide their identity

        mailSender.send(message);
        System.out.println("Mail Send...");
    }

    @Override
    public void sendMailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("anhhvth2010043@fpt.edu.vn");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource =
                new FileSystemResource(new File(attachment));//"D:\\Themes\\Anime\\1.jpg"

        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");
    }

    @Override
    public void sendMailWithoutAttachment(String toEmail, String body, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("anhhvth2010043@fpt.edu.vn");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");
    }

    @Override
    public MailResponse sendMailWithTemplate(MailRequest request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
            //helper.addAttachment("politecat.png", new ClassPathResource("politecat.png"));

            Template t = config.getTemplate("email-reset-password-success.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            mailSender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }

    @Override
    public MailResponse sendMailCheckoutSuccess(MailRequest request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate("send-mail-checkout-success.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            mailSender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }

    @Override
    public MailResponse sendMailCheckoutWhenBuyingSuccess(MailRequest request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate("send-mail-checkout-buy-success.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            mailSender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }

    @Override
    public MailResponse sendMailRequestedResetPassword(MailRequest request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate("request-reset-password.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            mailSender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }
}

