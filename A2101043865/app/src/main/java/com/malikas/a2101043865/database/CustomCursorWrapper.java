package com.malikas.a2101043865.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.malikas.a2101043865.model.Doctor;
import com.malikas.a2101043865.model.Nurse;
import com.malikas.a2101043865.model.Patient;
import com.malikas.a2101043865.model.Test;


public class CustomCursorWrapper extends CursorWrapper {
    public CustomCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Nurse getNurse() {
        int nurseId = getInt(getColumnIndex(DbSchema.NurseTable.Cols.NURSE_ID));
        String firstname = getString(getColumnIndex(DbSchema.NurseTable.Cols.FIRSTNAME));
        String lastname = getString(getColumnIndex(DbSchema.NurseTable.Cols.LASTNAME));
        String department = getString(getColumnIndex(DbSchema.NurseTable.Cols.DEPARTMENT));
        String username = getString(getColumnIndex(DbSchema.NurseTable.Cols.USERNAME));
        String password = getString(getColumnIndex(DbSchema.NurseTable.Cols.PASSWORD));

        Nurse nurse = new Nurse(nurseId, firstname, lastname, department, username, password);
        return nurse;
    }

    public int getLastIdNurse(){
        int nurseId = getInt(getColumnIndex(DbSchema.NurseTable.Cols.NURSE_ID));
        return nurseId;
    }

    public Doctor getDoctor() {
        int doctorId = getInt(getColumnIndex(DbSchema.DoctorTable.Cols.DOCTOR_ID));
        String firstname = getString(getColumnIndex(DbSchema.DoctorTable.Cols.FIRSTNAME));
        String lastname = getString(getColumnIndex(DbSchema.DoctorTable.Cols.LASTNAME));
        String department = getString(getColumnIndex(DbSchema.DoctorTable.Cols.DEPARTMENT));
        String username = getString(getColumnIndex(DbSchema.DoctorTable.Cols.USERNAME));
        String password = getString(getColumnIndex(DbSchema.DoctorTable.Cols.PASSWORD));

        Doctor doctor = new Doctor(doctorId, firstname, lastname, department, username, password);
        return doctor;
    }

    public int getLastIdDoctor(){
        int doctorId = getInt(getColumnIndex(DbSchema.DoctorTable.Cols.DOCTOR_ID));
        return doctorId;
    }

    public Patient getPatient() {
        int patientId = getInt(getColumnIndex(DbSchema.PatientTable.Cols.PATIENT_ID));
        String firstname = getString(getColumnIndex(DbSchema.PatientTable.Cols.FIRSTNAME));
        String lastname = getString(getColumnIndex(DbSchema.PatientTable.Cols.LASTNAME));
        String department = getString(getColumnIndex(DbSchema.PatientTable.Cols.DEPARTMENT));
        int nurseId = getInt(getColumnIndex(DbSchema.PatientTable.Cols.NURSE_ID));
        int doctorId = getInt(getColumnIndex(DbSchema.PatientTable.Cols.DOCTOR_ID));
        String room = getString(getColumnIndex(DbSchema.PatientTable.Cols.ROOM));

        Patient patient = new Patient(patientId, firstname, lastname, department,
                nurseId, doctorId, room);
        return patient;
    }

    public Test getTest() {
        int testId = getInt(getColumnIndex(DbSchema.TestTable.Cols.TEST_ID));
        int patientId = getInt(getColumnIndex(DbSchema.TestTable.Cols.PATIENT_ID));
        String BPL = getString(getColumnIndex(DbSchema.TestTable.Cols.BPL));
        String BPH = getString(getColumnIndex(DbSchema.TestTable.Cols.BPH));
        String temperature = getString(getColumnIndex(DbSchema.TestTable.Cols.TEMPERATURE));
        String HIV = getString(getColumnIndex(DbSchema.TestTable.Cols.HIV));
        String hepatitis = getString(getColumnIndex(DbSchema.TestTable.Cols.HEPATITIS));

        Test test = new Test(testId, patientId, BPL, BPH, temperature,
                HIV, hepatitis);
        return test;
    }

}
