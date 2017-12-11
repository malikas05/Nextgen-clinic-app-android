package com.malikas.a2101043865.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.malikas.a2101043865.R;
import com.malikas.a2101043865.model.Patient;
import com.malikas.a2101043865.model.Test;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class TestInfoFragment extends Fragment {

    public PatInfoFragment.Callbacks listener;
    private Patient patient;

    @BindView(R.id.textViewPat) TextView textViewPat;
    @BindView(R.id.BPL_Wrapper) TextInputLayout BPL_Wrapper;
    @BindView(R.id.BPH_Wrapper) TextInputLayout BPH_Wrapper;
    @BindView(R.id.tempWrapper) TextInputLayout tempWrapper;
    @BindView(R.id.HIV_Wrapper) TextInputLayout HIV_Wrapper;
    @BindView(R.id.hepatitisWrapper) TextInputLayout hepatitisWrapper;

    private String BPL;
    private String BPH;
    private String temp;
    private String HIV;
    private String hepatitis;

    public static TestInfoFragment newInstance(Patient patient){
        Bundle args = new Bundle();
        args.putParcelable("patientObject", patient);

        TestInfoFragment testInfoFragment = new TestInfoFragment();
        testInfoFragment.setArguments(args);
        return testInfoFragment;
    }

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
        BPL_Wrapper.getEditText().requestFocus();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patient = getArguments().getParcelable("patientObject");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_test, container, false);
        ButterKnife.bind(this, v);

        textViewPat.setText("Patient Name: " + patient.getFirstname() + " " + patient.getLastname());
        return v;
    }
    //

    // Listeners
    @OnClick(R.id.buttonAddTest)
    public void addNewTest(){
        if (checkFields()){
            Test test = new Test(0, patient.getPatientId(), BPL, BPH, temp, HIV, hepatitis);
            listener.addTest(test);
            Toast.makeText(getContext(), "New test for \"" + patient.getFirstname() + " " + patient.getLastname() +
                    "\" was added successfully", Toast.LENGTH_SHORT).show();
            listener.changeFragment(1, null);
        }
    }

    @OnTextChanged(R.id.editTextBPL)
    public void onTextChangedEditTextBPL() {
        BPL = BPL_Wrapper.getEditText().getText().toString();
        if (BPL.trim().isEmpty()){
            BPL_Wrapper.setError("Enter the BPL test for the patient");
        } else {
            BPL_Wrapper.setErrorEnabled(false);
        }

        if (BPL.length() >= 26)
            BPL_Wrapper.setError("You cannot enter more than 25 characters.");
    }

    @OnTextChanged(R.id.editTextBPH)
    public void onTextChangedEditTextBPH() {
        BPH = BPH_Wrapper.getEditText().getText().toString();
        if (BPH.trim().isEmpty()){
            BPH_Wrapper.setError("Enter the BPH test for the patient");
        } else {
            BPH_Wrapper.setErrorEnabled(false);
        }

        if (BPH.length() >= 26)
            BPH_Wrapper.setError("You cannot enter more than 25 characters.");
    }

    @OnTextChanged(R.id.editTextTemp)
    public void onTextChangedEditTextTemp() {
        temp = tempWrapper.getEditText().getText().toString();
        if (temp.trim().isEmpty()){
            tempWrapper.setError("Enter the temperature test for the patient");
        } else {
            tempWrapper.setErrorEnabled(false);
        }

        if (temp.length() >= 5)
            tempWrapper.setError("You cannot enter more than 4 characters.");
    }

    @OnTextChanged(R.id.editTextHIV)
    public void onTextChangedEditTextHIV() {
        HIV = HIV_Wrapper.getEditText().getText().toString();
        if (HIV.trim().isEmpty()){
            HIV_Wrapper.setError("Enter the HIV test for the patient");
        } else {
            HIV_Wrapper.setErrorEnabled(false);
        }

        if (HIV.length() >= 26)
            HIV_Wrapper.setError("You cannot enter more than 25 characters.");
    }

    @OnTextChanged(R.id.editTextHepatitis)
    public void onTextChangedEditTextHepatitis() {
        hepatitis = hepatitisWrapper.getEditText().getText().toString();
        if (hepatitis.trim().isEmpty()){
            hepatitisWrapper.setError("Enter the hepatitis test for the patient");
        } else {
            hepatitisWrapper.setErrorEnabled(false);
        }

        if (hepatitis.length() >= 26)
            hepatitisWrapper.setError("You cannot enter more than 25 characters.");
    }
    //

    private boolean checkFields(){
        boolean check = true;
        BPL = BPL_Wrapper.getEditText().getText().toString().trim();
        BPH = BPH_Wrapper.getEditText().getText().toString().trim();
        temp = tempWrapper.getEditText().getText().toString().trim();
        HIV = HIV_Wrapper.getEditText().getText().toString().trim();
        hepatitis = hepatitisWrapper.getEditText().getText().toString().trim();

        if (BPL.isEmpty() || BPL.length() > 25){
            BPL_Wrapper.setError("Error");
            BPL_Wrapper.getEditText().requestFocus();
            check = false;
        }
        else if (BPH.isEmpty() || BPH.length() > 25) {
            BPH_Wrapper.setError("Error");
            BPH_Wrapper.getEditText().requestFocus();
            check = false;
        }
        else if (temp.isEmpty() || temp.length() > 4) {
            tempWrapper.setError("Error");
            tempWrapper.getEditText().requestFocus();
            check = false;
        }
        else if (HIV.isEmpty() || HIV.length() > 25) {
            HIV_Wrapper.setError("Error");
            HIV_Wrapper.getEditText().requestFocus();
            check = false;
        }
        else if (hepatitis.isEmpty() || hepatitis.length() > 25) {
            hepatitisWrapper.setError("Error");
            hepatitisWrapper.getEditText().requestFocus();
            check = false;
        }

        if (check){
            BPL_Wrapper.setErrorEnabled(false);
            BPH_Wrapper.setErrorEnabled(false);
            tempWrapper.setErrorEnabled(false);
            HIV_Wrapper.setErrorEnabled(false);
            hepatitisWrapper.setErrorEnabled(false);
            return check;
        }
        return check;
    }
    //
}
