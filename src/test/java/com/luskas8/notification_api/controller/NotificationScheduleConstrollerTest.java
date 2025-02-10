package com.luskas8.notification_api.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luskas8.notification_api.controller.dto.NotificationScheduleRequestRecord;
import com.luskas8.notification_api.controller.dto.NotificationScheduleResponseRecord;
import com.luskas8.notification_api.infrastructure.enums.NotificationStatusEnum;
import com.luskas8.notification_api.service.NotificationScheduleService;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class NotificationScheduleConstrollerTest {
    @InjectMocks
    NotificationScheduleConstroller notificationScheduleConstroller;

    @Mock
    NotificationScheduleService service;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private NotificationScheduleRequestRecord notificationScheduleRequest;
    private NotificationScheduleResponseRecord notificationScheduleResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationScheduleConstroller).build();
        objectMapper.registerModule(new JavaTimeModule());

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
    void shouldCreateANotificationScheduleWithSuccess() throws Exception {
        when(service.save(notificationScheduleRequest)).thenReturn(notificationScheduleResponse);

        mockMvc.perform(
            post("/schedule")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(notificationScheduleRequest))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("id"))
        .andExpect(jsonPath("$.destinatary_email").value("email@email.com"))
        .andExpect(jsonPath("$.destinatary_phonenumber").value(notificationScheduleResponse.destinatary_phonenumber()))
        .andExpect(jsonPath("$.message").value(notificationScheduleResponse.message()))
        .andExpect(jsonPath("$.send_at").value("02-01-2025 11:01:01"))
        .andExpect(jsonPath("$.status").value("SCHEDULED"));

        verify(service, times(1)).save(notificationScheduleRequest);
    }
}
