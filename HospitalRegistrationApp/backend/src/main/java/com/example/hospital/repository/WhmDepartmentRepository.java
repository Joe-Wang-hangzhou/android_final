package com.example.hospital.repository;

import com.example.hospital.entity.WchDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhmDepartmentRepository extends JpaRepository<WchDepartment, Long> {
}
