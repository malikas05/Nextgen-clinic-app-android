package com.malikas.a2101043865.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.malikas.a2101043865.model.Doctor;
import com.malikas.a2101043865.model.Nurse;
import com.malikas.a2101043865.model.Patient;
import com.malikas.a2101043865.model.Test;

import java.util.ArrayList;
import java.util.List;


public class DataLab {

    private static DataLab dataLab;

    private Context appContext;
    private SQLiteDatabase database;

    private DataLab(Context appContext){
        this.appContext = appContext.getApplicationContext();
        database = new DatabaseHelper(appContext).getWritableDatabase();
    }

    public static DataLab get(Context c){
        if (dataLab == null)
            dataLab = new DataLab(c.getApplicationContext());
        return dataLab;
    }

    public Nurse getNurse(String username, String password){
        CustomCursorWrapper cursor = queryDB(DbSchema.NurseTable.NAME, null,
                DbSchema.NurseTable.Cols.USERNAME + " = ? AND " +
                DbSchema.NurseTable.Cols.PASSWORD + " = ?", new String[]{username, password}, null);
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getNurse();
        }
        finally {
            cursor.close();
        }
    }

    public Doctor getDoctor(String username, String password){
        CustomCursorWrapper cursor = queryDB(DbSchema.DoctorTable.NAME, null,
                DbSchema.DoctorTable.Cols.USERNAME + " = ? AND " +
                DbSchema.DoctorTable.Cols.PASSWORD + " = ?", new String[]{username, password}, null);
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getDoctor();
        }
        finally {
            cursor.close();
        }
    }

    public List<Patient> getPatientsByNurse(int nurseId){
        List<Patient> patients = new ArrayList<>();
        CustomCursorWrapper cursor = queryDB(DbSchema.PatientTable.NAME, null,
                DbSchema.PatientTable.Cols.NURSE_ID + " = ?", new String[]{String.valueOf(nurseId)}, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                patients.add(cursor.getPatient());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return patients;
    }

    public List<Patient> getPatientsByDoctor(int doctorId){
        List<Patient> patients = new ArrayList<>();
        CustomCursorWrapper cursor = queryDB(DbSchema.PatientTable.NAME, null,
                DbSchema.PatientTable.Cols.DOCTOR_ID + " = ?", new String[]{String.valueOf(doctorId)}, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                patients.add(cursor.getPatient());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return patients;
    }

    public List<Test> getTestsByPatient(int patientId){
        List<Test> tests = new ArrayList<>();
        CustomCursorWrapper cursor = queryDB(DbSchema.TestTable.NAME, null,
                DbSchema.TestTable.Cols.PATIENT_ID + " = ?", new String[]{String.valueOf(patientId)}, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tests.add(cursor.getTest());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return tests;
    }

    public List<Test> getTests(){
        List<Test> tests = new ArrayList<>();
        CustomCursorWrapper cursor = queryDB(DbSchema.TestTable.NAME, null,
               null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tests.add(cursor.getTest());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return tests;
    }

    public Nurse addNurse(Nurse nurse){
        nurse.setNurseId(getLastIdNurse());
        ContentValues values = getContentValuesNurse(nurse);
        database.insert(DbSchema.NurseTable.NAME, null, values);
        nurse.setUsername(values.get(DbSchema.NurseTable.Cols.USERNAME).toString());
        nurse.setPassword(values.get(DbSchema.NurseTable.Cols.PASSWORD).toString());
        return nurse;
    }

    public Doctor addDoctor(Doctor doctor){
        doctor.setDoctorId(getLastIdDoctor());
        ContentValues values = getContentValuesDoctor(doctor);
        database.insert(DbSchema.DoctorTable.NAME, null, values);
        doctor.setUsername(values.get(DbSchema.DoctorTable.Cols.USERNAME).toString());
        doctor.setPassword(values.get(DbSchema.DoctorTable.Cols.PASSWORD).toString());
        return doctor;
    }

    public void addPatient(Patient patient){
        ContentValues values = getContentValuesPatient(patient);
        database.insert(DbSchema.PatientTable.NAME, null, values);
    }

    public void addTest(Test test){
        ContentValues values = getContentValuesTest(test);
        database.insert(DbSchema.TestTable.NAME, null, values);
    }

    public int getLastIdNurse(){
        CustomCursorWrapper cursor = queryDB(DbSchema.NurseTable.NAME, new String[]{DbSchema.NurseTable.Cols.NURSE_ID},
                null, null, null);
        try {
            if (cursor.getCount() == 0)
                return 1;
            cursor.moveToLast();
            return cursor.getLastIdNurse() + 1;
        }
        finally {
            cursor.close();
        }
    }

    public int getLastIdDoctor(){
        CustomCursorWrapper cursor = queryDB(DbSchema.DoctorTable.NAME, new String[]{DbSchema.DoctorTable.Cols.DOCTOR_ID},
                null, null, null);
        try {
            if (cursor.getCount() == 0)
                return 1;
            cursor.moveToLast();
            return cursor.getLastIdDoctor() + 1;
        }
        finally {
            cursor.close();
        }
    }

    private CustomCursorWrapper queryDB(String dbName, String[] columns, String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = database.query(
                dbName,
                columns, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                orderBy // orderBy
        );
        return new CustomCursorWrapper(cursor);
    }

    // filling ContentValues for models
    private static ContentValues getContentValuesNurse(Nurse nurse) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.NurseTable.Cols.FIRSTNAME, nurse.getFirstname());
        values.put(DbSchema.NurseTable.Cols.LASTNAME, nurse.getLastname());
        values.put(DbSchema.NurseTable.Cols.DEPARTMENT, nurse.getDepartment());
        values.put(DbSchema.NurseTable.Cols.USERNAME, generateUsername(nurse.getFirstname(), nurse.getLastname(), "N"));
        values.put(DbSchema.NurseTable.Cols.PASSWORD, generatePassword(nurse.getFirstname(), nurse.getLastname(), nurse.getNurseId()));
        return values;
    }

    private static ContentValues getContentValuesDoctor(Doctor doctor) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.DoctorTable.Cols.FIRSTNAME, doctor.getFirstname());
        values.put(DbSchema.DoctorTable.Cols.LASTNAME, doctor.getLastname());
        values.put(DbSchema.DoctorTable.Cols.DEPARTMENT, doctor.getDepartment());
        values.put(DbSchema.DoctorTable.Cols.USERNAME, generateUsername(doctor.getFirstname(), doctor.getLastname(), "D"));
        values.put(DbSchema.DoctorTable.Cols.PASSWORD, generatePassword(doctor.getFirstname(), doctor.getLastname(), doctor.getDoctorId()));
        return values;
    }

    private static ContentValues getContentValuesPatient(Patient patient) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.PatientTable.Cols.FIRSTNAME, patient.getFirstname());
        values.put(DbSchema.PatientTable.Cols.LASTNAME, patient.getLastname());
        values.put(DbSchema.PatientTable.Cols.DEPARTMENT, patient.getDepartment());
        values.put(DbSchema.PatientTable.Cols.NURSE_ID, patient.getNurseId());
        values.put(DbSchema.PatientTable.Cols.DOCTOR_ID, patient.getDoctorId());
        values.put(DbSchema.PatientTable.Cols.ROOM, patient.getRoom());
        return values;
    }

    private static ContentValues getContentValuesTest(Test test) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.TestTable.Cols.PATIENT_ID, test.getPatientId());
        values.put(DbSchema.TestTable.Cols.BPL, test.getBPL());
        values.put(DbSchema.TestTable.Cols.BPH, test.getBPH());
        values.put(DbSchema.TestTable.Cols.TEMPERATURE, test.getTemperature());
        values.put(DbSchema.TestTable.Cols.HIV, test.getHIV());
        values.put(DbSchema.TestTable.Cols.HEPATITIS, test.getHepatitis());
        return values;
    }

    private static String generateUsername(String firstname, String lastname, String cat){
        firstname = firstname.toLowerCase();
        lastname = lastname.toLowerCase();
        if (firstname.length() > 3)
            firstname = firstname.substring(0, 3);
        if (lastname.length() > 3)
            lastname = lastname.substring(0, 3);
        String username = firstname + cat + lastname;
        return username;
    }

    private static String generatePassword(String firstname, String lastname, int id){
        firstname = firstname.toLowerCase();
        lastname = lastname.toLowerCase();
        if (firstname.length() > 3)
            firstname = firstname.substring(0, 3);
        if (lastname.length() > 3)
            lastname = lastname.substring(0, 3);
        String password = lastname + firstname + id;
        return password;
    }
}
