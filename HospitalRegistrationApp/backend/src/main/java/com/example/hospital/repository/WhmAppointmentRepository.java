package com.example.hospital.repository;

import com.example.hospital.entity.WchAppointment;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WhmAppointmentRepository extends JpaRepository<WchAppointment, Long> {
    List<WchAppointment> findByUser_IdOrderByCreateTimeDesc(Long userId);

    @Query("select count(a) > 0 from WchAppointment a where a.patientPhone = :patientPhone and a.schedule.workDate = :workDate and a.schedule.timePeriod = :timePeriod and a.status = '已预约')")
    boolean existsActiveAppointmentAtSameTime(@Param("patientPhone") String patientPhone,
                                              @Param("workDate") LocalDate workDate,
                                              @Param("timePeriod") String timePeriod);
}
