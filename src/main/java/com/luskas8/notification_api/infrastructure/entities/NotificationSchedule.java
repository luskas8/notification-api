package com.luskas8.notification_api.infrastructure.entities;

import java.time.LocalDateTime;

import com.luskas8.notification_api.infrastructure.enums.NotificationStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "notification_schedule")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String message;
    private String destinatary_email;
    private String destinatary_phonenumber;
    private LocalDateTime send_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private NotificationStatusEnum status;

    @PrePersist
    private void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.created_at = now;
        this.updated_at = now;
        this.status = NotificationStatusEnum.SCHEDULED;
    }
}
