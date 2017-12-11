package com.malikas.a2101043865.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "hospital.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final String CREATE_TABLE_PATIENT = "CREATE TABLE "
            + DbSchema.PatientTable.NAME + "(" + DbSchema.PatientTable.Cols.PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.PatientTable.Cols.FIRSTNAME + " TEXT," + DbSchema.PatientTable.Cols.LASTNAME + " TEXT,"
            + DbSchema.PatientTable.Cols.DEPARTMENT + " TEXT,"
            + DbSchema.PatientTable.Cols.NURSE_ID + " INTEGER,"
            + DbSchema.PatientTable.Cols.DOCTOR_ID + " INTEGER,"
            + DbSchema.PatientTable.Cols.ROOM + " TEXT" + ")";

    private static final String CREATE_TABLE_TEST = "CREATE TABLE "
            + DbSchema.TestTable.NAME + "(" + DbSchema.TestTable.Cols.TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.TestTable.Cols.PATIENT_ID + " INTEGER," + DbSchema.TestTable.Cols.BPL + " TEXT,"
            + DbSchema.TestTable.Cols.BPH + " TEXT,"
            + DbSchema.TestTable.Cols.TEMPERATURE + " TEXT,"
            + DbSchema.TestTable.Cols.HIV + " TEXT,"
            + DbSchema.TestTable.Cols.HEPATITIS + " TEXT" + ")";

    private static final String CREATE_TABLE_NURSE = "CREATE TABLE "
            + DbSchema.NurseTable.NAME + "(" + DbSchema.NurseTable.Cols.NURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.NurseTable.Cols.FIRSTNAME + " TEXT," + DbSchema.NurseTable.Cols.LASTNAME + " TEXT,"
            + DbSchema.NurseTable.Cols.DEPARTMENT + " TEXT,"
            + DbSchema.NurseTable.Cols.USERNAME + " TEXT,"
            + DbSchema.NurseTable.Cols.PASSWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_DOCTOR = "CREATE TABLE "
            + DbSchema.DoctorTable.NAME + "(" + DbSchema.DoctorTable.Cols.DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.DoctorTable.Cols.FIRSTNAME + " TEXT," + DbSchema.DoctorTable.Cols.LASTNAME + " TEXT,"
            + DbSchema.DoctorTable.Cols.DEPARTMENT + " TEXT,"
            + DbSchema.NurseTable.Cols.USERNAME + " TEXT,"
            + DbSchema.NurseTable.Cols.PASSWORD + " TEXT" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PATIENT);
        db.execSQL(CREATE_TABLE_TEST);
        db.execSQL(CREATE_TABLE_NURSE);
        db.execSQL(CREATE_TABLE_DOCTOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.PatientTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.TestTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.NurseTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.DoctorTable.NAME);

        onCreate(db);
    }
}
