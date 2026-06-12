package com.example.hospital.dto;

public class WchAppointmentRequest {
    private long userId;
    private long doctorId;
    private long scheduleId;
    private String patientName;
    private String patientPhone;

    public WchAppointmentRequest() {}

    public WchAppointmentRequest(long userId, long doctorId, long scheduleId, String patientName, String patientPhone) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.scheduleId = scheduleId;
        this.patientName = patientName;
        this.patientPhone = patientPhone;
    }

    public long getUserId() { return userId; }
    public long getDoctorId() { return doctorId; }
    public long getScheduleId() { return scheduleId; }
    public String getPatientName() { return patientName; }
    public String getPatientPhone() { return patientPhone; }
    public void setUserId(long userId) { this.userId = userId; }
    public void setDoctorId(long doctorId) { this.doctorId = doctorId; }
    public void setScheduleId(long scheduleId) { this.scheduleId = scheduleId; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setPatientPhone(String patientPhone) { this.patientPhone = patientPhone; }
}
