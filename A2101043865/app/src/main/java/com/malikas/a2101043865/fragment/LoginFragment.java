package com.malikas.a2101043865.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.malikas.a2101043865.PatientActivity;
import com.malikas.a2101043865.R;
import com.malikas.a2101043865.SingleFragmentActivity;
import com.malikas.a2101043865.model.Doctor;
import com.malikas.a2101043865.model.Nurse;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;



public class LoginFragment extends Fragment {

    public interface Callbacks{
        void changeFragment(int fragmentNum);
        Nurse getNurse(String username, String password);
        Doctor getDoctor(String username, String password);
        Nurse addNurse(String firstname, String lastname, String dep);
        Doctor addDoctor(String firstname, String lastname, String dep);
    }

    public Callbacks listener;

    @BindView(R.id.loginWrapper) TextInputLayout loginWrapper;
    @BindView(R.id.passwordWrapper) TextInputLayout passwordWrapper;

    private String username;
    private String password;

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
        loginWrapper.getEditText().requestFocus();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);

        return v;
    }
    //

    // Listeners
    @OnClick(R.id.buttonHint)
    public void showHint(){
        buildAlertDialog("Info", "You can use admin credentials to create new nurse/doctor", android.R.drawable.ic_dialog_info);
    }

    @OnClick(R.id.buttonLogin)
    public void login(){
        boolean check = true;
        if (checkFields()){
            if (username.equals("admin") && password.equals("root")){
                listener.changeFragment(1);
            }
            else {
                if (username.matches("(.{3})N(.{3})")){
                    Nurse nurse = listener.getNurse(username, password);
                    if (nurse != null) {
                        Toast.makeText(getContext(), "Hi " + nurse.getFirstname() + " " + nurse.getLastname() + "!",
                                Toast.LENGTH_SHORT).show();
                        saveToSP(nurse);
                    }
                    else
                        check = false;
                }
                else if (username.matches("(.{3})D(.{3})")){
                    Doctor doctor = listener.getDoctor(username, password);
                    if (doctor != null) {
                        Toast.makeText(getContext(), "Hi " + doctor.getFirstname() + " " + doctor.getLastname() + "!",
                                Toast.LENGTH_SHORT).show();
                        saveToSP(doctor);
                    }
                    else
                        check = false;
                }
                else {
                    loginWrapper.setError("Enter the username matching the format {***N***} or {***D***}");
                    return;
                }

                if (check){
                    Intent patActivity = new Intent(getContext(), PatientActivity.class);
                    patActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(patActivity);
                    getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    getActivity().finish();
                }
                else
                    Toast.makeText(getContext(), "You are not registered.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnTextChanged(R.id.editTextLogin)
    public void onTextChangedEditTextLogin() {
        username = loginWrapper.getEditText().getText().toString();
        if (username.trim().isEmpty()){
            loginWrapper.setError("Enter your username");
        } else {
            loginWrapper.setErrorEnabled(false);
        }

        if (username.length() >= 26)
            loginWrapper.setError("You cannot enter more than 25 characters.");
    }

    @OnTextChanged(R.id.editTextPassword)
    public void onTextChangedEditTextPassword() {
        password = passwordWrapper.getEditText().getText().toString();
        if (password.trim().isEmpty()){
            passwordWrapper.setError("Enter your password");
        } else {
            passwordWrapper.setErrorEnabled(false);
        }

        if (password.length() >= 16)
            passwordWrapper.setError("You cannot enter more than 15 characters.");
    }
    //

    // When we press login button, this method checks our input on the client side
    private boolean checkFields(){
        boolean check = true;
        username = loginWrapper.getEditText().getText().toString().trim();
        password = passwordWrapper.getEditText().getText().toString().trim();

        if (username.isEmpty() || username.length() > 25){
            loginWrapper.setError("Error");
            loginWrapper.getEditText().requestFocus();
            check = false;
        }
        else if (password.isEmpty() || password.length() > 15) {
            passwordWrapper.setError("Error");
            passwordWrapper.getEditText().requestFocus();
            check = false;
        }

        if (check){
            loginWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);
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

    public void saveToSP(Object object){
        SharedPreferences.Editor prefsEditor = SingleFragmentActivity.mPrefs.edit();
        if (object instanceof Nurse) {
            prefsEditor.putString("MyObjectType", "N");
        }
        else if (object instanceof Doctor) {
            prefsEditor.putString("MyObjectType", "D");
        }
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();
    }
}
