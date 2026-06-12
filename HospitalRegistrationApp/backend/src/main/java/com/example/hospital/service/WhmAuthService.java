package com.example.hospital.service;

import com.example.hospital.dto.WchApiResponse;
import com.example.hospital.dto.WchLoginRequest;
import com.example.hospital.dto.WchRegisterRequest;
import com.example.hospital.entity.WchUser;
import com.example.hospital.repository.WhmUserRepository;
import org.springframework.stereotype.Service;

@Service
public class WhmAuthService {
    private final WhmUserRepository userRepository;

    public WhmAuthService(WhmUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public WchApiResponse<WchUser> register(WchRegisterRequest request) {
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            return new WchApiResponse<>(false, "手机号已注册", null);
        }
        WchUser user = new WchUser();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        return new WchApiResponse<>(true, null, userRepository.save(user));
    }

    public WchApiResponse<WchUser> login(WchLoginRequest request) {
        WchUser user = userRepository.findByPhone(request.getPhone()).orElse(null);
        if (user == null) {
            return new WchApiResponse<>(false, "用户不存在", null);
        }
        if (!user.getPassword().equals(request.getPassword())) {
            return new WchApiResponse<>(false, "密码错误", null);
        }
        return new WchApiResponse<>(true, null, user);
    }
}
