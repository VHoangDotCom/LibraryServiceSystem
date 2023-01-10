package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.Notification;
import com.library.repository.NotificationRepository;
import com.library.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        Calendar cal = Calendar.getInstance();
        notification.setCreatedAt(cal.getTime());
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
       return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getAllNotificationByUserID(Long userID) {
        return notificationRepository.getAllNotificationByUserID(userID);
    }

    @Override
    public String deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id).get();
        if(notification == null){
            return "Cannot find Notification " +id;
        }else{
            notificationRepository.delete(notification);
            return "Notification "+id+ " has been deleted !";
        }
    }

    @Override
    public Notification updateNotification(Long id, Notification notification) {
        Notification notificationExisted = notificationRepository.findById(id).get();
        notificationExisted.setContent(notification.getContent());

        notificationRepository.save(notificationExisted);
        return notificationExisted;
    }
}
