package com.example.hospital.repository;

import com.example.hospital.entity.WchDoctor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhmDoctorRepository extends JpaRepository<WchDoctor, Long> {
    List<WchDoctor> findByDepartment_Id(Long departmentId);
}
