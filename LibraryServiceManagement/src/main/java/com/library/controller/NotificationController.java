package com.library.controller;


import com.library.entity.Book;
import com.library.entity.Category;
import com.library.entity.Notification;
import com.library.entity.User;
import com.library.repository.NotificationRepository;
import com.library.repository.UserRepository;
import com.library.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<?> getNotificationByID(@PathVariable Long id){
        if(notificationRepository.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(notificationRepository.findById(id));
        }
    }

    @GetMapping("/notifications/user/{userId}")
    public ResponseEntity<?> getNotificationByUserID(@PathVariable Long userId){
        if(notificationService.getAllNotificationByUserID(userId) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List Notifications is empty!");
        }else{
            return ResponseEntity.ok().body(notificationService.getAllNotificationByUserID(userId));
        }
    }

    @PostMapping("/notifications/add")
    public Notification createNotification(@RequestParam("userId") Long userId ,@RequestBody Notification notification) {
        User userFind = userRepository.findById(userId).get();
        notification.setUser(userFind);
        System.out.println(notification);
        return notificationService.createNotification(notification);
    }

    @DeleteMapping("/notifications/delete/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }

    @PutMapping("/notifications/save/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id,
                                           @RequestBody Notification notification) {
        notification = notificationService.updateNotification(id, notification);
        return ResponseEntity.ok(notification);
    }
}
