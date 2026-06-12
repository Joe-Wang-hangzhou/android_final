package com.example.hospital.repository;

import com.example.hospital.entity.WchUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhmUserRepository extends JpaRepository<WchUser, Long> {
    Optional<WchUser> findByPhone(String phone);
}
