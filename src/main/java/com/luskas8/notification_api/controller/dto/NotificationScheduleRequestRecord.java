package com.luskas8.notification_api.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record NotificationScheduleRequestRecord(
    String message,
    String destinatary_email,
    String destinatary_phonenumber,
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime send_at
) {

}
