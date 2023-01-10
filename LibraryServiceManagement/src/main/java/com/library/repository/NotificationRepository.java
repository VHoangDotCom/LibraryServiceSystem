package com.library.repository;

import com.library.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(
            value = "SELECT * FROM notification s where s.user_id = :userId",
            nativeQuery = true
    )
    List<Notification> getAllNotificationByUserID(Long userId);
}
