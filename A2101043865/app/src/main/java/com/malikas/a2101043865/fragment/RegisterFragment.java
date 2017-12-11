package com.malikas.a2101043865.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.malikas.a2101043865.R;
import com.malikas.a2101043865.model.Doctor;
import com.malikas.a2101043865.model.Nurse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;



public class RegisterFragment extends Fragment {

    public LoginFragment.Callbacks listener;

    @BindView(R.id.firstnameWrapper) TextInputLayout firstnameWrapper;
    @BindView(R.id.lastnameWrapper) TextInputLayout lastnameWrapper;
    @BindView(R.id.spinnerCat) Spinner spinnerCat;
    @BindView(R.id.spinnerDep) Spinner spinnerDep;

    private String firstname;
    private String lastname;

    //lifecycle methods
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (LoginFragment.Callbacks)context;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, v);

        return v;
    }
    //

    // Listeners
    @OnClick(R.id.buttonReg)
    public void register(){
        if (checkFields()){
            if (spinnerCat.getSelectedItem().toString().equals("nurses")) {
                Nurse nurse = listener.addNurse(firstname, lastname, spinnerDep.getSelectedItem().toString());
                buildAlertDialog("Information", "Nurse \"" + firstname + " " + lastname + "\" was added successfully. " +
                                "\nCredentials: \n - Username: " + nurse.getUsername() + "\n - Password: " + nurse.getPassword(),
                        android.R.drawable.ic_dialog_info);
            }
            else if (spinnerCat.getSelectedItem().toString().equals("doctors")) {
                Doctor doctor = listener.addDoctor(firstname, lastname, spinnerDep.getSelectedItem().toString());
                buildAlertDialog("Information", "Doctor \"" + firstname + " " + lastname + "\" was added successfully. " +
                                "\nCredentials: \n - Username: " + doctor.getUsername() + "\n - Password: " + doctor.getPassword(),
                        android.R.drawable.ic_dialog_info);
            }
            listener.changeFragment(2);
        }
    }

    @OnTextChanged(R.id.editTextFN)
    public void onTextChangedEditTextFN() {
        firstname = firstnameWrapper.getEditText().getText().toString();
        if (firstname.trim().isEmpty()){
            firstnameWrapper.setError("Enter your first name");
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
            lastnameWrapper.setError("Enter your last name");
        } else {
            lastnameWrapper.setErrorEnabled(false);
        }

        if (lastname.length() >= 16)
            lastnameWrapper.setError("You cannot enter more than 15 characters.");
    }
    //

    private boolean checkFields(){
        boolean check = true;
        firstname = firstnameWrapper.getEditText().getText().toString().trim();
        lastname = lastnameWrapper.getEditText().getText().toString().trim();

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

        if (check){
            firstnameWrapper.setErrorEnabled(false);
            lastnameWrapper.setErrorEnabled(false);
            return check;
        }
        return check;
    }
    //

    private void buildAlertDialog(String title, String message, int resId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setIcon(resId);
        builder.setMessage(message);
        builder.show();
    }
}
