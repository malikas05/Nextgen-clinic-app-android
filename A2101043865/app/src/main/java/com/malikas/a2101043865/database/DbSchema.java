package com.malikas.a2101043865.database;


public class DbSchema {

    public static final class PatientTable{
        public static final String NAME = "Patient";

        public static final class Cols{
            public static final String PATIENT_ID = "patientId";
            public static final String FIRSTNAME = "firstname";
            public static final String LASTNAME = "lastname";
            public static final String DEPARTMENT = "department";
            public static final String NURSE_ID = "nurseId";
            public static final String DOCTOR_ID = "doctorId";
            public static final String ROOM = "room";
        }
    }

    public static final class TestTable{
        public static final String NAME = "Test";

        public static final class Cols{
            public static final String TEST_ID = "testId";
            public static final String PATIENT_ID = "patientId";
            public static final String BPL = "BPL";
            public static final String BPH = "BPH";
            public static final String TEMPERATURE = "temperature";
            public static final String HIV = "HIV";
            public static final String HEPATITIS = "hepatitis";
        }
    }

    public static final class NurseTable{
        public static final String NAME = "Nurse";

        public static final class Cols{
            public static final String NURSE_ID = "nurseId";
            public static final String FIRSTNAME = "firstname";
            public static final String LASTNAME = "lastname";
            public static final String DEPARTMENT = "department";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }

    public static final class DoctorTable{
        public static final String NAME = "Doctor";

        public static final class Cols{
            public static final String DOCTOR_ID = "doctorId";
            public static final String FIRSTNAME = "firstname";
            public static final String LASTNAME = "lastname";
            public static final String DEPARTMENT = "department";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }
}
