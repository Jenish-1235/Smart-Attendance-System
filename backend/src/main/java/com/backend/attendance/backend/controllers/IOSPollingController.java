package com.backend.attendance.backend.controllers;

import com.backend.attendance.backend.models.IOSPollingRequest;
import com.backend.attendance.backend.services.IOSPollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/attendance/wifi")
public class IOSPollingController {

    @Autowired
    private IOSPollingService iosPollingService;

    @PostMapping("/wifi")
    public ResponseEntity<?> wifiAttendancePolling(@RequestBody IOSPollingRequest iOSPollingRequest) throws SQLException {
        return ResponseEntity.ok().body(iosPollingService.poll(iOSPollingRequest.getEmail(), iOSPollingRequest.getBssid()));
    }
}
