package com.luskas8.notification_api.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luskas8.notification_api.infrastructure.entities.NotificationSchedule;

public interface NotificationScheduleRepository extends JpaRepository<NotificationSchedule, String> {

}
