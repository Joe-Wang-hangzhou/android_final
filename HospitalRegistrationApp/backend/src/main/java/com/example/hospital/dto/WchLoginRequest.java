package com.example.hospital.dto;

public class WchLoginRequest {
    private String phone;
    private String password;

    public WchLoginRequest() {}

    public WchLoginRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPassword(String password) { this.password = password; }
}
