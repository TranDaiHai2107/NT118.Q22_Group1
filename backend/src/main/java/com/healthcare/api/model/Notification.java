package com.healthcare.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "NOTIFICATION_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "notification_id", unique = true)
    private String notificationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "notification_type")
    private String type;

    private String title;

    @Column(columnDefinition = "CLOB")
    private String message;

    @Column(name = "notification_time")
    private String time;

    @Column(name = "is_read", columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean isRead;

    public Notification() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
}
