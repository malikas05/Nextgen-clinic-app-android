package com.malikas.a2101043865.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.malikas.a2101043865.LogRegActivity;
import com.malikas.a2101043865.PatientActivity;
import com.malikas.a2101043865.R;
import com.malikas.a2101043865.SingleFragmentActivity;
import com.malikas.a2101043865.model.Doctor;
import com.malikas.a2101043865.model.Nurse;
import com.malikas.a2101043865.model.Patient;
import com.malikas.a2101043865.model.Test;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class PatInfoFragment extends Fragment {

    public interface Callbacks{
        void changeFragment(int fragmentNum, Patient patient);
        List<Patient> getPatientsByNurse(int nurseId);
        List<Patient> getPatientsByDoctor(int doctorId);
        void addPatient(Patient patient);
        void addTest(Test test);
        List<Test> getTestsByPatient(int patientId);
        List<Test> getTests();
    }

    public PatInfoFragment.Callbacks listener;

    @BindView(R.id.patientInfo) TextView patientInfo;
    @BindView(R.id.testInfo) TextView testInfo;
    @BindView(R.id.textViewUser) TextView textViewUser;
    @BindView(R.id.spinnerPat) Spinner spinnerPat;

    private Nurse nurse;
    private Doctor doctor;
    private Patient patient;
    List<Patient> patients = new ArrayList<>();

    //lifecycle methods
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Callbacks)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Gson gson = new Gson();
        switch (PatientActivity.type){
            case "N":
                nurse = gson.fromJson(SingleFragmentActivity.mPrefs.getString("MyObject", ""), Nurse.class);
                break;
            case "D":
                doctor = gson.fromJson(SingleFragmentActivity.mPrefs.getString("MyObject", ""), Doctor.class);
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pag_info, container, false);
        ButterKnife.bind(this, v);

        switch (PatientActivity.type){
            case "N":
                textViewUser.setText("Hi, " + nurse.getFirstname() + " " + nurse.getLastname() + " - Nurse");
                break;
            case "D":
                textViewUser.setText("Hi, " + doctor.getFirstname() + " " + doctor.getLastname() + " - Doctor");
                break;
        }

        fillSpinnerPat();
        if (patients.isEmpty()){
            patientInfo.setText("No patients in the database.");
            testInfo.setText("No tests in the database.");
        }
        return v;
    }
    //

    private void fillSpinnerPat(){
        if (PatientActivity.type.equals("N"))
            patients = listener.getPatientsByNurse(nurse.getNurseId());
        else if (PatientActivity.type.equals("D"))
            patients = listener.getPatientsByDoctor(doctor.getDoctorId());
        String[] names = new String[patients.size()];
        for (int i = 0; i < patients.size(); i++) {
            names[i] = patients.get(i).getFirstname() + " " + patients.get(i).getLastname();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                Arrays.asList(names));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPat.setAdapter(dataAdapter);

        spinnerPat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String patientInfoStr = "";

                patient = patients.get(i);
                patientInfoStr = String.format("Patient Information: \n - First Name: %s \n - Last Name: %s \n" +
                                " - Department: %s \n - Room: %s \n",
                        patient.getFirstname(), patient.getLastname(), patient.getDepartment(), patient.getRoom());
                patientInfo.setText(patientInfoStr);

                String testsInfo = "Tests Information: \n";
                int count = 1;
                List<Test> tests = listener.getTestsByPatient(patient.getPatientId());
                if (tests != null && !tests.isEmpty()){
                    for (Test test : tests){
                        testsInfo += buildTestString(count++, test);
                    }
                    testInfo.setText(testsInfo);
                }
                else
                    testInfo.setText("No tests for current patient");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.patient_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_patient:
                listener.changeFragment(2, null);
                return true;
            case R.id.menu_item_new_test:
                if (!patients.isEmpty())
                    listener.changeFragment(3, patient);
                else
                    Toast.makeText(getActivity(), "You cannot add a test because there is no patient in the database",
                            Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_logout:
                SingleFragmentActivity.mPrefs.edit().clear().commit();
                Intent intent = new Intent(getActivity(), LogRegActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //

    private String buildTestString(int num, Test test){
        return String.format("Test #%d: \n - BPL: %s \n - BPH: %s \n - Temperature: %s \n - HIV: %s \n - Hepatitis: %s \n\n",
                num, test.getBPL(), test.getBPH(), test.getTemperature(), test.getHIV(), test.getHepatitis());
    }

}
