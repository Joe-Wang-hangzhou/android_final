package com.example.hospital.service;

import com.example.hospital.dto.WchApiResponse;
import com.example.hospital.dto.WchAppointmentRequest;
import com.example.hospital.entity.WchAppointment;
import com.example.hospital.entity.WchDoctor;
import com.example.hospital.entity.WchSchedule;
import com.example.hospital.entity.WchUser;
import com.example.hospital.repository.WhmAppointmentRepository;
import com.example.hospital.repository.WhmDoctorRepository;
import com.example.hospital.repository.WhmScheduleRepository;
import com.example.hospital.repository.WhmUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WhmAppointmentService {
    private final WhmAppointmentRepository appointmentRepository;
    private final WhmUserRepository userRepository;
    private final WhmDoctorRepository doctorRepository;
    private final WhmScheduleRepository scheduleRepository;

    public WhmAppointmentService(WhmAppointmentRepository appointmentRepository,
                                 WhmUserRepository userRepository,
                                 WhmDoctorRepository doctorRepository,
                                 WhmScheduleRepository scheduleRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public WchApiResponse<WchAppointment> create(WchAppointmentRequest request) {
        if (request.getPatientName() == null || request.getPatientName().trim().isEmpty()) {
            return new WchApiResponse<>(false, "请填写就诊人姓名", null);
        }
        if (request.getPatientPhone() == null || request.getPatientPhone().trim().isEmpty()) {
            return new WchApiResponse<>(false, "请填写就诊人手机号", null);
        }
        WchUser user = userRepository.findById(request.getUserId()).orElse(null);
        WchDoctor doctor = doctorRepository.findById(request.getDoctorId()).orElse(null);
        WchSchedule schedule = scheduleRepository.findById(request.getScheduleId()).orElse(null);
        if (user == null || doctor == null || schedule == null) {
            return new WchApiResponse<>(false, "预约信息不完整", null);
        }
        boolean exists = appointmentRepository.existsActiveAppointmentAtSameTime(
                request.getPatientPhone(),
                schedule.getWorkDate(),
                schedule.getTimePeriod()
        );
        if (exists) {
            return new WchApiResponse<>(false, "该就诊人在当前日期和时段已有预约，不能重复挂号", null);
        }
        if (schedule.getLeftNumber() == null || schedule.getLeftNumber() <= 0) {
            return new WchApiResponse<>(false, "当前排班号源已满", null);
        }
        schedule.setLeftNumber(schedule.getLeftNumber() - 1);
        scheduleRepository.save(schedule);

        WchAppointment appointment = new WchAppointment();
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setSchedule(schedule);
        appointment.setPatientName(request.getPatientName());
        appointment.setPatientPhone(request.getPatientPhone());
        appointment.setStatus("已预约");
        appointment.setCreateTime(LocalDateTime.now());
        return new WchApiResponse<>(true, null, appointmentRepository.save(appointment));
    }

    public List<WchAppointment> findByUser(Long userId) {
        return appointmentRepository.findByUser_IdOrderByCreateTimeDesc(userId);
    }

    @Transactional
    public WchApiResponse<WchAppointment> cancel(long id) {
        WchAppointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            return new WchApiResponse<>(false, "预约记录不存在", null);
        }
        if ("已取消".equals(appointment.getStatus())) {
            return new WchApiResponse<>(false, "预约已取消", null);
        }
        appointment.setStatus("已取消");
        WchSchedule schedule = appointment.getSchedule();
        if (schedule != null) {
            schedule.setLeftNumber(schedule.getLeftNumber() + 1);
            scheduleRepository.save(schedule);
        }
        return new WchApiResponse<>(true, null, appointmentRepository.save(appointment));
    }
}
