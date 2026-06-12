package com.example.hospital.controller;

import com.example.hospital.entity.WchDepartment;
import com.example.hospital.entity.WchDoctor;
import com.example.hospital.entity.WchNotice;
import com.example.hospital.entity.WchSchedule;
import com.example.hospital.repository.WhmDepartmentRepository;
import com.example.hospital.repository.WhmDoctorRepository;
import com.example.hospital.repository.WhmNoticeRepository;
import com.example.hospital.repository.WhmScheduleRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WhmHospitalController {
    private final WhmNoticeRepository noticeRepository;
    private final WhmDepartmentRepository departmentRepository;
    private final WhmDoctorRepository doctorRepository;
    private final WhmScheduleRepository scheduleRepository;

    public WhmHospitalController(WhmNoticeRepository noticeRepository,
                                 WhmDepartmentRepository departmentRepository,
                                 WhmDoctorRepository doctorRepository,
                                 WhmScheduleRepository scheduleRepository) {
        this.noticeRepository = noticeRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping("/notices")
    public List<WchNotice> notices() {
        return noticeRepository.findAllByOrderByPublishTimeDesc();
    }

    @GetMapping("/departments")
    public List<WchDepartment> departments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/doctors")
    public List<WchDoctor> doctors(@RequestParam(required = false) Long departmentId) {
        if (departmentId == null) {
            return doctorRepository.findAll();
        }
        return doctorRepository.findByDepartment_Id(departmentId);
    }

    @GetMapping("/doctors/{id}")
    public WchDoctor doctor(@PathVariable long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @GetMapping("/schedules")
    public List<WchSchedule> schedules(@RequestParam Long doctorId) {
        return scheduleRepository.findByDoctor_Id(doctorId);
    }
}
