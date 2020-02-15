package com.marty.track.Profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.marty.track.R;

public class ProfileActivity extends AppCompatActivity {

    TextView driverName, driverPhone, driverDOB, driverAddress, driverEmail, driverPassword, driverSchoolName,
            driverNumber, driverJoiningDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        String driverfname = bundle.getString("driver_fname");
        String driverlname = bundle.getString("driver_lname");
        String driverphone = bundle.getString("driver_phone");
        String driverdob = bundle.getString("driver_dob");
        String driveraddress = bundle.getString("driver_address");
        String drivercity = bundle.getString("driver_city");
        String driveremail = bundle.getString("driver_email");
        String driverpassword = bundle.getString("driver_password");
        String driverschoolname = bundle.getString("driver_sid");
        String drivernumber = bundle.getString("driver_number");
        String driverjoiningdate = bundle.getString("driver_jdate");

        driverName = findViewById(R.id.tdrivername);
        driverName.setText(driverfname + " "+driverlname);

        driverPhone = findViewById(R.id.tdriverphone);
        driverPhone.setText(driverphone);

        driverDOB = findViewById(R.id.tdriverdob);
        driverDOB.setText(driverdob);

        driverAddress = findViewById(R.id.tdriveraddress);
        driverAddress.setText(driveraddress +", "+drivercity);

        driverEmail = findViewById(R.id.tdriveremail);
        driverEmail.setText(driveremail);

        driverPassword = findViewById(R.id.tdriverpassword);
        driverPassword.setText(driverpassword);

        driverSchoolName = findViewById(R.id.tdriverschool);
        driverSchoolName.setText(driverschoolname);

        driverNumber = findViewById(R.id.tdrivernumber);
        driverNumber.setText(drivernumber);

        driverJoiningDate = findViewById(R.id.tdriverjoining);
        driverJoiningDate.setText(driverjoiningdate);

    }
}
