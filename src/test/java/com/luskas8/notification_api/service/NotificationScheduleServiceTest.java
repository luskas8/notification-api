package com.luskas8.notification_api.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luskas8.notification_api.controller.dto.NotificationScheduleRequestRecord;
import com.luskas8.notification_api.controller.dto.NotificationScheduleResponseRecord;
import com.luskas8.notification_api.infrastructure.entities.NotificationSchedule;
import com.luskas8.notification_api.infrastructure.enums.NotificationStatusEnum;
import com.luskas8.notification_api.infrastructure.repositories.NotificationScheduleRepository;
import com.luskas8.notification_api.mapper.INotificationScheduleMapper;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class NotificationScheduleServiceTest {
    @InjectMocks
    NotificationScheduleService notificationScheduleService;

    @Mock
    NotificationScheduleRepository notificationScheduleRepository;
    @Mock
    INotificationScheduleMapper notificationScheduleMapper;

    private NotificationScheduleRequestRecord notificationScheduleRequest;
    private NotificationScheduleResponseRecord notificationScheduleResponse;
    private NotificationSchedule notificationSchedule;
    @BeforeEach
    void setUp() {
        notificationSchedule = new NotificationSchedule(
            "id",
            "message",
            "destinatary_email",
            "destinatary_phonenumber",
            LocalDateTime.of(2025, 1, 2, 11, 1, 1),
            LocalDateTime.of(2025, 1, 2, 11, 1, 1),
            LocalDateTime.of(2025, 1, 2, 11, 1, 1),
            NotificationStatusEnum.SCHEDULED
        );

        notificationScheduleRequest = new NotificationScheduleRequestRecord(
            "Favor retornar a loja com urgência",
            "email@email.com",
            "55887996578",
            LocalDateTime.of(2025, 1, 2, 11, 1, 1)
        );

        notificationScheduleResponse = new NotificationScheduleResponseRecord(
            "id",
            "Favor retornar a loja com urgência",
            "email@email.com",
            "55887996578",
            LocalDateTime.of(2025, 1, 2, 11, 1, 1),
            LocalDateTime.of(2025, 1, 2, 11, 1, 1),
            NotificationStatusEnum.SCHEDULED
        );
    }

    @Test
    void shouldSaveNotificationSchedulerWithSuccess() {
        when(notificationScheduleMapper.toEntity(notificationScheduleRequest)).thenReturn(notificationSchedule);
        when(notificationScheduleRepository.save(notificationSchedule)).thenReturn(notificationSchedule);
        when(notificationScheduleMapper.toResponse(notificationSchedule)).thenReturn(notificationScheduleResponse);

        NotificationScheduleResponseRecord out = notificationScheduleService.save(notificationScheduleRequest);

        verify(notificationScheduleMapper, times(1)).toEntity(notificationScheduleRequest);
        verify(notificationScheduleRepository, times(1)).save(notificationSchedule);
        verify(notificationScheduleMapper, times(1)).toResponse(notificationSchedule);
        assertThat(out).usingRecursiveComparison().isEqualTo(notificationScheduleResponse);
    }
}
