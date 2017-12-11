package com.malikas.a2101043865.model;


public class Doctor {

    private int doctorId;
    private String firstname;
    private String lastname;
    private String department;
    private String username;
    private String password;

    public Doctor() {

    }

    public Doctor(int doctorId, String firstname, String lastname, String department, String username, String password) {
        this.doctorId = doctorId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.username = username;
        this.password = password;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int nurseId) {
        this.doctorId = nurseId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
