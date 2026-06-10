package com.example.hospital.service;

import com.example.hospital.dto.ApiResponse;
import com.example.hospital.dto.LoginRequest;
import com.example.hospital.dto.RegisterRequest;
import com.example.hospital.entity.User;
import com.example.hospital.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse<User> register(RegisterRequest request) {
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            return ApiResponse.fail("手机号已注册");
        }
        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        return ApiResponse.ok(userRepository.save(user));
    }

    public ApiResponse<User> login(LoginRequest request) {
        User user = userRepository.findByPhone(request.getPhone()).orElse(null);
        if (user == null) {
            return ApiResponse.fail("用户不存在");
        }
        if (!user.getPassword().equals(request.getPassword())) {
            return ApiResponse.fail("密码错误");
        }
        return ApiResponse.ok(user);
    }
}
