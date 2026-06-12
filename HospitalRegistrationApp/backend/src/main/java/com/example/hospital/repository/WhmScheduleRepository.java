package com.example.hospital.repository;

import com.example.hospital.entity.WchSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhmScheduleRepository extends JpaRepository<WchSchedule, Long> {
    List<WchSchedule> findByDoctor_Id(Long doctorId);
}
