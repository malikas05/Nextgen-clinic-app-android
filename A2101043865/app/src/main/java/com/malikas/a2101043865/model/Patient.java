package com.malikas.a2101043865.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Patient implements Parcelable {

    private int patientId;
    private String firstname;
    private String lastname;
    private String department;
    private int nurseId;
    private int doctorId;
    private String room;

    public Patient() {

    }

    public Patient(Parcel in) {
        this.patientId = in.readInt();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.department = in.readString();
        this.nurseId = in.readInt();
        this.doctorId = in.readInt();
        this.room = in.readString();
    }

    public Patient(int patientId, String firstname, String lastname,
                   String department, int nurseId, int doctorId, String room) {
        this.patientId = patientId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.nurseId = nurseId;
        this.doctorId = doctorId;
        this.room = room;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(patientId);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(department);
        parcel.writeInt(nurseId);
        parcel.writeInt(doctorId);
        parcel.writeString(room);
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}
