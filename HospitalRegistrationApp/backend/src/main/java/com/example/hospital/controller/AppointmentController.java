package com.example.hospital.controller;

import com.example.hospital.dto.ApiResponse;
import com.example.hospital.dto.AppointmentRequest;
import com.example.hospital.entity.Appointment;
import com.example.hospital.service.AppointmentService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ApiResponse<Appointment> create(@RequestBody AppointmentRequest request) {
        return appointmentService.create(request);
    }

    @GetMapping("/user/{userId}")
    public List<Appointment> findByUser(@PathVariable Long userId) {
        return appointmentService.findByUser(userId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Appointment> cancel(@PathVariable Long id) {
        return appointmentService.cancel(id);
    }
}
