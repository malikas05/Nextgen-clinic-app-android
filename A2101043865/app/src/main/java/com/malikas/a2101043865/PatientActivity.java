package com.malikas.a2101043865;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.malikas.a2101043865.database.DataLab;
import com.malikas.a2101043865.fragment.PatInfoFragment;
import com.malikas.a2101043865.fragment.PatNewFragment;
import com.malikas.a2101043865.fragment.TestInfoFragment;
import com.malikas.a2101043865.model.Patient;
import com.malikas.a2101043865.model.Test;

import java.util.List;



public class PatientActivity extends SingleFragmentActivity implements PatInfoFragment.Callbacks{

    private DataLab dataLab;

    // method for replacing fragments
    @Override
    public void changeFragment(int fragmentNum, Patient patient) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentNum == 1)
            ft.replace(R.id.fragmentContainerMain, new PatInfoFragment());
        else if (fragmentNum == 2)
            ft.replace(R.id.fragmentContainerMain, new PatNewFragment());
        else if (fragmentNum == 3)
            ft.replace(R.id.fragmentContainerMain, TestInfoFragment.newInstance(patient));
        ft.addToBackStack(null);
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public List<Patient> getPatientsByNurse(int nurseId) {
        return dataLab.getPatientsByNurse(nurseId);
    }

    @Override
    public List<Patient> getPatientsByDoctor(int doctorId) {
        return dataLab.getPatientsByDoctor(doctorId);
    }

    @Override
    public void addPatient(Patient patient) {
        dataLab.addPatient(patient);
    }

    @Override
    public void addTest(Test test) {
        dataLab.addTest(test);
    }

    @Override
    public List<Test> getTestsByPatient(int patientId) {
        return dataLab.getTestsByPatient(patientId);
    }

    @Override
    public List<Test> getTests() {
        return dataLab.getTests();
    }

    protected Fragment createFragment(){
        return new PatInfoFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataLab = DataLab.get(this);
    }

}
