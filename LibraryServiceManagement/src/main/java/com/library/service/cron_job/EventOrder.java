package com.library.service.cron_job;

import com.library.entity.OrderItem;
import com.library.entity.email.MailResponse;
import com.library.service.OrderItemService;
import com.library.service.OrderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableScheduling
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
@Component
public class EventOrder {

    private final JavaMailSender mailSender;

    private Configuration config;

    private final OrderItemService orderItemService;

    private final OrderService orderService;

    private final Map<String, Object> model;

    private static String[] Bcc_Mail = {

    };

//    @Scheduled(cron = "* */1 * * * *")// Fire at 6:30 AM every day : 0 30 6 ? * *
//    public void  sendMailToRunningOutOfDateOrder() throws MessagingException {
//        //Working normally
//        List<String> Bcc_mail = new ArrayList<String>();
//        List<OrderItem> orderItemList = orderItemService.getListRunningOutDateOrderItem();
//
//        for(OrderItem orderItem :orderItemList){
//            Bcc_mail.add(orderItem.getOrder().getEmail());
//        }
//
//        Bcc_Mail = Bcc_mail.toArray(new String[0]);
//
//        MailResponse response = new MailResponse();
//        MimeMessage message = mailSender.createMimeMessage();
//        if(orderItemList != null){
//            try {
//                // set mediaType
//                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                        StandardCharsets.UTF_8.name());
//
//                Template t = config.getTemplate("mail-order-running-out-of-date.ftl");
//                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//
//                helper.setText(html, true);
//                helper.setSubject("Xin chao");
//                helper.setFrom("viethoang2001gun@gmail.com");
//                helper.setBcc(Bcc_Mail);
//                mailSender.send(message);
//                log.info("Every minute!!");
//                response.setStatus(Boolean.TRUE);
//
//            } catch (MessagingException | IOException | TemplateException e) {
//                response.setMessage("Mail Sending failure : "+e.getMessage());
//                response.setStatus(Boolean.FALSE);
//            }
//        }
//    }


}
