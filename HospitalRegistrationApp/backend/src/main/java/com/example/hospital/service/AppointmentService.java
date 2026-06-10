package com.example.hospital.service;

import com.example.hospital.dto.ApiResponse;
import com.example.hospital.dto.AppointmentRequest;
import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Schedule;
import com.example.hospital.entity.User;
import com.example.hospital.repository.AppointmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.ScheduleRepository;
import com.example.hospital.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              DoctorRepository doctorRepository,
                              ScheduleRepository scheduleRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public ApiResponse<Appointment> create(AppointmentRequest request) {
        if (request.getPatientName() == null || request.getPatientName().trim().isEmpty()) {
            return ApiResponse.fail("请填写就诊人姓名");
        }
        if (request.getPatientPhone() == null || request.getPatientPhone().trim().isEmpty()) {
            return ApiResponse.fail("请填写就诊人手机号");
        }
        User user = userRepository.findById(request.getUserId()).orElse(null);
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElse(null);
        Schedule schedule = scheduleRepository.findById(request.getScheduleId()).orElse(null);
        if (user == null || doctor == null || schedule == null) {
            return ApiResponse.fail("预约信息不完整");
        }
        boolean exists = appointmentRepository.existsActiveAppointmentAtSameTime(
                request.getPatientPhone(),
                schedule.getWorkDate(),
                schedule.getTimePeriod()
        );
        if (exists) {
            return ApiResponse.fail("该就诊人在当前日期和时段已有预约，不能重复挂号");
        }
        if (schedule.getLeftNumber() == null || schedule.getLeftNumber() <= 0) {
            return ApiResponse.fail("当前排班号源已满");
        }
        schedule.setLeftNumber(schedule.getLeftNumber() - 1);
        scheduleRepository.save(schedule);

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setSchedule(schedule);
        appointment.setPatientName(request.getPatientName());
        appointment.setPatientPhone(request.getPatientPhone());
        appointment.setStatus("已预约");
        appointment.setCreateTime(LocalDateTime.now());
        return ApiResponse.ok(appointmentRepository.save(appointment));
    }

    public List<Appointment> findByUser(Long userId) {
        return appointmentRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    @Transactional
    public ApiResponse<Appointment> cancel(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            return ApiResponse.fail("预约记录不存在");
        }
        if ("已取消".equals(appointment.getStatus())) {
            return ApiResponse.fail("预约已取消");
        }
        appointment.setStatus("已取消");
        Schedule schedule = appointment.getSchedule();
        schedule.setLeftNumber(schedule.getLeftNumber() + 1);
        scheduleRepository.save(schedule);
        return ApiResponse.ok(appointmentRepository.save(appointment));
    }
}
