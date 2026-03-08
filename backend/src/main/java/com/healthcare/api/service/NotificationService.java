package com.healthcare.api.service;

import com.healthcare.api.model.Notification;
import com.healthcare.api.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByIdDesc(userId);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId).size();
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsRead(userId);
    }
}
