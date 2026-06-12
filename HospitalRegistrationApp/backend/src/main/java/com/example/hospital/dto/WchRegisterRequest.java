package com.example.hospital.dto;

public class WchRegisterRequest {
    private String name;
    private String phone;
    private String password;

    public WchRegisterRequest() {}

    public WchRegisterRequest(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPassword(String password) { this.password = password; }
}
