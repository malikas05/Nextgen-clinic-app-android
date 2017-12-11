package com.malikas.a2101043865.model;



public class Nurse {

    private int nurseId;
    private String firstname;
    private String lastname;
    private String department;
    private String username;
    private String password;

    public Nurse() {

    }

    public Nurse(int nurseId, String firstname, String lastname, String department, String username, String password) {
        this.nurseId = nurseId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.username = username;
        this.password = password;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
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
