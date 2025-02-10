package com.luskas8.notification_api.service;

import org.springframework.stereotype.Service;

import com.luskas8.notification_api.infrastructure.entities.NotificationSchedule;
import com.luskas8.notification_api.infrastructure.repositories.NotificationScheduleRepository;
import com.luskas8.notification_api.controller.dto.NotificationScheduleRequestRecord;
import com.luskas8.notification_api.controller.dto.NotificationScheduleResponseRecord;
import com.luskas8.notification_api.exception.NotFoundException;
import com.luskas8.notification_api.mapper.INotificationScheduleMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationScheduleService {
    private final NotificationScheduleRepository notificationScheduleRepository;
    private final INotificationScheduleMapper notificationScheduleMapper;

    public NotificationScheduleResponseRecord save(NotificationScheduleRequestRecord schedule) {
        return notificationScheduleMapper.toResponse(
            notificationScheduleRepository.save(
                notificationScheduleMapper.toEntity(schedule)
            )
        );
    }

    public NotificationScheduleResponseRecord findById(String id) {
        return notificationScheduleMapper.toResponse(
            notificationScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Notification schedule not found"))
        );
    }

    public void cancelById(String id) {
        NotificationSchedule notification = notificationScheduleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Notification schedule not found"));
        
        notificationScheduleRepository.save(notificationScheduleMapper.toCancel(notification));
    }
}
