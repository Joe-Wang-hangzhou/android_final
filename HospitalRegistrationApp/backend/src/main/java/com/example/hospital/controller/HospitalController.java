package com.example.hospital.controller;

import com.example.hospital.entity.Department;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Notice;
import com.example.hospital.entity.Schedule;
import com.example.hospital.repository.DepartmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.NoticeRepository;
import com.example.hospital.repository.ScheduleRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HospitalController {
    private final NoticeRepository noticeRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    public HospitalController(NoticeRepository noticeRepository,
                              DepartmentRepository departmentRepository,
                              DoctorRepository doctorRepository,
                              ScheduleRepository scheduleRepository) {
        this.noticeRepository = noticeRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping("/notices")
    public List<Notice> notices() {
        return noticeRepository.findAllByOrderByPublishTimeDesc();
    }

    @GetMapping("/departments")
    public List<Department> departments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/doctors")
    public List<Doctor> doctors(@RequestParam(required = false) Long departmentId) {
        if (departmentId == null) {
            return doctorRepository.findAll();
        }
        return doctorRepository.findByDepartmentId(departmentId);
    }

    @GetMapping("/doctors/{id}")
    public Doctor doctor(@PathVariable Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @GetMapping("/schedules")
    public List<Schedule> schedules(@RequestParam Long doctorId) {
        return scheduleRepository.findByDoctorId(doctorId);
    }
}
