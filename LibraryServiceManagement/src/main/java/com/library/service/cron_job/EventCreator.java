package com.library.service.cron_job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Transactional
@Slf4j
@Component
public class EventCreator {

    private static final Logger LOG = LoggerFactory.getLogger(EventCreator.class);
     /*
    ** CronTab Expression meaning
    * Seconds - Minutes - Hours - Day of month - Month - Day of week - Year
    *   0 0 12 * * ?	            Fire at 12:00 PM (noon) every day
        0 15 10 ? * *	            Fire at 10:15 AM every day
        0 15 10 * * ?	            Fire at 10:15 AM every day
        0 15 10 * * ? *	            Fire at 10:15 AM every day
        0 15 10 * * ? 2005	        Fire at 10:15 AM every day during the year 2005
        0 * 14 * * ?	            Fire every minute starting at 2:00 PM and ending at 2:59 PM, every day
        0 0/5 14 * * ?	            Fire every 5 minutes starting at 2:00 PM and ending at 2:55 PM, every day
        0 0/5 14,18 * * ?	        Fire every 5 minutes starting at 2:00 PM and ending at 2:55 PM, AND fire every 5 minutes starting at 6:00 PM and ending at 6:55 PM, every day
        0 0-5 14 * * ?	            Fire every minute starting at 2:00 PM and ending at 2:05 PM, every day
        0 10,44 14 ? 3 WED	        Fire at 2:10 PM and at 2:44 PM every Wednesday in the month of March
        0 15 10 ? * MON-FRI	        Fire at 10:15 AM every Monday, Tuesday, Wednesday, Thursday and Friday
        0 15 10 15 * ?	            Fire at 10:15 AM on the 15th day of every month
        0 15 10 L * ?	            Fire at 10:15 AM on the last day of every month
        0 15 10 ? * 6L	            Fire at 10:15 AM on the last Friday of every month
        0 15 10 ? * 6L 2002-2005	Fire at 10:15 AM on every last friday of every month during the years 2002, 2003, 2004, and 2005
        0 15 10 ? * 6#3	            Fire at 10:15 AM on the third Friday of every month
        0 0 12 1/5 * ?	            Fire at 12 PM (noon) every 5 days every month, starting on the first day of the month
        0 11 11 11 11 ?	            Fire every November 11 at 11:11 AM
     */

    /*
     * Thread.sleep()
     * 1000 : 1 second
     * 1000L : 1 second (L means it is a long literal. 1000 is an int.
     * It will automatically be promoted to long.
     */

    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")

    //private final MailService mailService;

//    @Scheduled(cron = "*/1 * * * * *")//every seconds
//    public void  triggerMail() throws MessagingException, InterruptedException {
//        mailService.sendMailWithAttachment("viethoang2001gun@gmail.com",
//                "You login with wrong password !",
//                "Hi Viet Hoang",
//                "D:\\Themes\\Anime\\1.jpg");
//        Thread.sleep(1000L);
//    }

//    @Scheduled(cron = " 0 0/1 * * * *")//every 1 minute
//    public void  triggerEveryMinute() {
//        log.info("Every minute!!");
//    }

}
