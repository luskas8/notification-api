package com.luskas8.notification_api.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.luskas8.notification_api.controller.dto.NotificationScheduleRequestRecord;
import com.luskas8.notification_api.controller.dto.NotificationScheduleResponseRecord;
import com.luskas8.notification_api.infrastructure.entities.NotificationSchedule;

@Mapper(componentModel = SPRING)
public interface INotificationScheduleMapper {

    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    @Mapping(target = "id", ignore = true)
    NotificationSchedule toEntity(NotificationScheduleRequestRecord notificationSchedule);

    NotificationScheduleResponseRecord toResponse(NotificationSchedule notificationSchedule);

    @Mapping(target = "status", expression = "java(NotificationStatusEnum.CANCELED)")
    @Mapping(target = "updated_at", expression = "java(LocalDateTime.now())")
    NotificationSchedule toCancel(NotificationSchedule notificationSchedule);
}
