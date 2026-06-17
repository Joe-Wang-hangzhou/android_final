package com.example.hospital.repository;

import com.example.hospital.entity.WchDepartment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhmDepartmentRepository extends JpaRepository<WchDepartment, Long> {
    List<WchDepartment> findByHospitalName(String hospitalName);
}
