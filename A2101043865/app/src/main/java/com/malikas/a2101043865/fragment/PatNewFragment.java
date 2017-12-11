package com.malikas.a2101043865.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.malikas.a2101043865.PatientActivity;
import com.malikas.a2101043865.R;
import com.malikas.a2101043865.SingleFragmentActivity;
import com.malikas.a2101043865.model.Doctor;
import com.malikas.a2101043865.model.Nurse;
import com.malikas.a2101043865.model.Patient;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;



public class PatNewFragment extends Fragment {

    public PatInfoFragment.Callbacks listener;

    @BindView(R.id.firstnameWrapper) TextInputLayout firstnameWrapper;
    @BindView(R.id.lastnameWrapper) TextInputLayout lastnameWrapper;
    @BindView(R.id.roomWrapper) TextInputLayout roomWrapper;
    @BindView(R.id.spinnerDep) Spinner spinnerDep;

    private String firstname;
    private String lastname;
    private String room;

    private Nurse nurse;
    private Doctor doctor;

    //lifecycle methods
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (PatInfoFragment.Callbacks)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        firstnameWrapper.getEditText().requestFocus();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View v = inflater.inflate(R.layout.fragment_new_patient, container, false);
        ButterKnife.bind(this, v);

        return v;
    }
    //

    // Listeners
    @OnClick(R.id.buttonAddPat)
    public void addNewPat(){
        if (checkFields()){
            if (PatientActivity.type.equals("N")){
                Patient patient = new Patient(0, firstname, lastname, spinnerDep.getSelectedItem().toString(),
                        nurse.getNurseId(), 0, room);
                listener.addPatient(patient);
                Toast.makeText(getContext(), patient.getFirstname() + " " + patient.getLastname() +
                        " was added successfully", Toast.LENGTH_SHORT).show();
                listener.changeFragment(1, null);
            }
            else if (PatientActivity.type.equals("D")){
                Patient patient = new Patient(0, firstname, lastname, spinnerDep.getSelectedItem().toString(),
                        0, doctor.getDoctorId(), room);
                listener.addPatient(patient);
                Toast.makeText(getContext(), patient.getFirstname() + " " + patient.getLastname() +
                        " was added successfully", Toast.LENGTH_SHORT).show();
                listener.changeFragment(1, null);
            }
        }
    }

    @OnTextChanged(R.id.editTextFN)
    public void onTextChangedEditTextFN() {
        firstname = firstnameWrapper.getEditText().getText().toString();
        if (firstname.trim().isEmpty()){
            firstnameWrapper.setError("Enter the first name of the patient");
        } else {
            firstnameWrapper.setErrorEnabled(false);
        }

        if (firstname.length() >= 16)
            firstnameWrapper.setError("You cannot enter more than 15 characters.");
    }

    @OnTextChanged(R.id.editTextLN)
    public void onTextChangedEditTextLN() {
        lastname = lastnameWrapper.getEditText().getText().toString();
        if (lastname.trim().isEmpty()){
            lastnameWrapper.setError("Enter the last name of the patient");
        } else {
            lastnameWrapper.setErrorEnabled(false);
        }

        if (lastname.length() >= 16)
            lastnameWrapper.setError("You cannot enter more than 15 characters.");
    }

    @OnTextChanged(R.id.editTextRoom)
    public void onTextChangedEditTextRoom() {
        room = roomWrapper.getEditText().getText().toString();
        if (room.trim().isEmpty()){
            roomWrapper.setError("Enter the room for the patient");
        } else {
            roomWrapper.setErrorEnabled(false);
        }

        if (room.length() >= 16)
            roomWrapper.setError("You cannot enter more than 15 characters.");
    }
    //

    private boolean checkFields(){
        boolean check = true;
        firstname = firstnameWrapper.getEditText().getText().toString().trim();
        lastname = lastnameWrapper.getEditText().getText().toString().trim();
        room = roomWrapper.getEditText().getText().toString().trim();

        if (firstname.isEmpty() || firstname.length() > 15){
            firstnameWrapper.setError("Error");
            firstnameWrapper.getEditText().requestFocus();
            check = false;
        }
        else if (lastname.isEmpty() || lastname.length() > 15) {
            lastnameWrapper.setError("Error");
            lastnameWrapper.getEditText().requestFocus();
            check = false;
        }
        else if (room.isEmpty() || room.length() > 15) {
            roomWrapper.setError("Error");
            roomWrapper.getEditText().requestFocus();
            check = false;
        }

        if (check){
            firstnameWrapper.setErrorEnabled(false);
            lastnameWrapper.setErrorEnabled(false);
            roomWrapper.setErrorEnabled(false);
            return check;
        }
        return check;
    }
    //
}
