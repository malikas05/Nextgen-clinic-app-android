package com.malikas.a2101043865;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.malikas.a2101043865.database.DataLab;
import com.malikas.a2101043865.fragment.LoginFragment;
import com.malikas.a2101043865.fragment.RegisterFragment;
import com.malikas.a2101043865.model.Doctor;
import com.malikas.a2101043865.model.Nurse;


public class LogRegActivity extends SingleFragmentActivity implements LoginFragment.Callbacks{

    private DataLab dataLab;

    // method for replacing fragments
    @Override
    public void changeFragment(int fragmentNum) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentNum == 1)
            ft.replace(R.id.fragmentContainerMain, new RegisterFragment());
        else if (fragmentNum == 2)
            ft.replace(R.id.fragmentContainerMain, new LoginFragment());
        ft.addToBackStack(null);
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public Nurse getNurse(String username, String password) {
        return dataLab.getNurse(username, password);
    }

    @Override
    public Doctor getDoctor(String username, String password) {
        return dataLab.getDoctor(username, password);
    }

    @Override
    public Nurse addNurse(String firstname, String lastname, String dep) {
        Nurse nurse = new Nurse();
        nurse.setFirstname(firstname);
        nurse.setLastname(lastname);
        nurse.setDepartment(dep);
        return dataLab.addNurse(nurse);
    }

    @Override
    public Doctor addDoctor(String firstname, String lastname, String dep) {
        Doctor doctor = new Doctor();
        doctor.setFirstname(firstname);
        doctor.setLastname(lastname);
        doctor.setDepartment(dep);
        return dataLab.addDoctor(doctor);
    }

    protected Fragment createFragment(){
        return new LoginFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataLab = DataLab.get(this);

        if (!type.isEmpty()){
            Intent patActivity = new Intent(this, PatientActivity.class);
            patActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(patActivity);
            this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            this.finish();
        }
    }
}
