package com.malikas.a2101043865.model;



public class Test {

    private int testId;
    private int patientId;
    private String BPL;
    private String BPH;
    private String temperature;
    private String HIV;
    private String hepatitis;

    public Test() {
    }

    public Test(int testId, int patientId, String BPL, String BPH, String temperature, String HIV, String hepatitis) {
        this.testId = testId;
        this.patientId = patientId;
        this.BPL = BPL;
        this.BPH = BPH;
        this.temperature = temperature;
        this.HIV = HIV;
        this.hepatitis = hepatitis;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getBPL() {
        return BPL;
    }

    public void setBPL(String BPL) {
        this.BPL = BPL;
    }

    public String getBPH() {
        return BPH;
    }

    public void setBPH(String BPH) {
        this.BPH = BPH;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHIV() {
        return HIV;
    }

    public void setHIV(String HIV) {
        this.HIV = HIV;
    }

    public String getHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(String hepatitis) {
        this.hepatitis = hepatitis;
    }
}
