package com.example.hospital.controller;

import com.example.hospital.dto.WchApiResponse;
import com.example.hospital.dto.WchLoginRequest;
import com.example.hospital.dto.WchRegisterRequest;
import com.example.hospital.entity.WchUser;
import com.example.hospital.service.WhmAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class WhmAuthController {
    private final WhmAuthService authService;

    public WhmAuthController(WhmAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public WchApiResponse<WchUser> register(@RequestBody WchRegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public WchApiResponse<WchUser> login(@RequestBody WchLoginRequest request) {
        return authService.login(request);
    }
}
