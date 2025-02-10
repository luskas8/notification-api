package com.luskas8.notification_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luskas8.notification_api.controller.dto.NotificationScheduleRequestRecord;
import com.luskas8.notification_api.controller.dto.NotificationScheduleResponseRecord;
import com.luskas8.notification_api.service.NotificationScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class NotificationScheduleConstroller {
    private final NotificationScheduleService notificationScheduleService;

    @PostMapping
    @Operation(description = "Save a new notification schedule")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification schedule saved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<NotificationScheduleResponseRecord> save(@RequestBody NotificationScheduleRequestRecord schedule) {
        return ResponseEntity.ok(notificationScheduleService.save(schedule));
    }

    @GetMapping("/{id}/status")
    @Operation(description = "Search a notification schedule")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification schedule"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Notification schedule not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<NotificationScheduleResponseRecord> index(@PathVariable("id") String id) {
        return ResponseEntity.ok(notificationScheduleService.findById(id));
    }

    @DeleteMapping("/{id}/cancel")
    @Operation(description = "Search a notification schedule")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "Notification schedule canceled"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Notification schedule not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> cancel(@PathVariable("id") String id) {
        notificationScheduleService.cancelById(id);
        return ResponseEntity.accepted().build();
    }
}
