package com.library.service;

import com.library.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);

    List<Notification> getAllNotifications();
    List<Notification> getAllNotificationByUserID(Long userID);

    String deleteNotification(Long id);

    Notification updateNotification(Long id, Notification notification);
}
