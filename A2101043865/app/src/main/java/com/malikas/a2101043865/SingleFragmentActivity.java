package com.malikas.a2101043865;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;



public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    public static SharedPreferences mPrefs = null;
    public static String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrefs = getSharedPreferences("MyApp_Settings", MODE_PRIVATE);
        type = mPrefs.getString("MyObjectType", "");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainerMain);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainerMain, fragment)
                    .commit();
        }
    }
}
