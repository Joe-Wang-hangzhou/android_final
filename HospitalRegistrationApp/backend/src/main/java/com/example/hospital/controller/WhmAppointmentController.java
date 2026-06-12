package com.example.hospital.controller;

import com.example.hospital.dto.WchApiResponse;
import com.example.hospital.dto.WchAppointmentRequest;
import com.example.hospital.entity.WchAppointment;
import com.example.hospital.service.WhmAppointmentService;
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
public class WhmAppointmentController {
    private final WhmAppointmentService appointmentService;

    public WhmAppointmentController(WhmAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public WchApiResponse<WchAppointment> create(@RequestBody WchAppointmentRequest request) {
        return appointmentService.create(request);
    }

    @GetMapping("/user/{userId}")
    public List<WchAppointment> findByUser(@PathVariable Long userId) {
        return appointmentService.findByUser(userId);
    }

    @DeleteMapping("/{id}")
    public WchApiResponse<WchAppointment> cancel(@PathVariable Long id) {
        return appointmentService.cancel(id);
    }
}
