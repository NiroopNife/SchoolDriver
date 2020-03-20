package com.marty.track.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.marty.track.R;

public class ProfileActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "SchoolPrefs";
    TextView driverName, driverPhone, driverAddress, driverSchoolName, driverNumber, driverJoiningDate;
    String driverfname, driverlname, driverphone, driveraddress, drivercity, driverschoolname, drivernumber,
            driverjoiningdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        driverfname = preferences.getString("driver_fname", "");
        driverlname = preferences.getString("driver_lname", "");
        driverphone = preferences.getString("driver_phone", "");
        driveraddress = preferences.getString("driver_address", "");
        drivercity = preferences.getString("driver_city","");
        driverschoolname = preferences.getString("driver_sid", "");
        drivernumber = preferences.getString("driver_id", "");
        driverjoiningdate = preferences.getString("driver_jdate", "");

        driverName = findViewById(R.id.tdrivername);
        driverName.setText(driverfname + " "+driverlname);

        driverPhone = findViewById(R.id.tdriverphone);
        driverPhone.setText(driverphone);


        driverAddress = findViewById(R.id.tdriveraddress);
        driverAddress.setText(driveraddress +", "+drivercity);


        driverSchoolName = findViewById(R.id.tdriverschool);
        driverSchoolName.setText(driverschoolname);

        driverNumber = findViewById(R.id.tdrivernumber);
        driverNumber.setText(drivernumber);

        driverJoiningDate = findViewById(R.id.tdriverjoining);
        driverJoiningDate.setText(driverjoiningdate);

    }
}
