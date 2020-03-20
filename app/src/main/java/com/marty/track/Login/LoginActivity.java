package com.marty.track.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.marty.track.ForgotPassword.PhaseOne.EmailActivity;
import com.marty.track.Main.HomeActivity;
import com.marty.track.Main.SharedPrefs;
import com.marty.track.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "SchoolPrefs";
    EditText editPassword, editUsername;
    TextView forgotPassword;
    ImageButton submit;
    String schoolID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        schoolID = preferences.getString("ad_pkid", "");

        editUsername = findViewById(R.id.etusername);
        editPassword = findViewById(R.id.etpassword);

        forgotPassword = findViewById(R.id.forgot);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, EmailActivity.class));
            }
        });

        submit = findViewById(R.id.login);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...!");
        dialog.show();

        final String username = editUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final String schoolid = schoolID;

        if (TextUtils.isEmpty(username)){
            editUsername.setError("Please enter the Username");
            editUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            editPassword.setError("Please enter the password");
            editPassword.requestFocus();
            return;
        }

        Retrofit retrofit = LoginClient.driverLogin();
        LoginService service = retrofit.create(LoginService.class);
        Call<LoginResponse> call = service.driverLogin(username, password, schoolid);
        System.out.println("Username : "+username +", Password : "+ password +", School ID"+ schoolid);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println(response);
                if (response.isSuccessful()){
                    String driverID = "";
                    String driverSID = "";
                    String driverNumber = "";
                    String driverPhone = "";
                    String driverFname = "";
                    String driverLname = "";
                    String driverEmail = "";
                    String driverPassword = "";
                    String driverJdate = "";
                    String driverDOB = "";
                    String driverAddress = "";
                    String driverPostCode = "";
                    String driverCity = "";
                    String driverCountry = "";
                    String driverCid = "";
                    String driverMdate = "";
                    String driverMid = "";
                    String driverIsActive = "";
                    String pdName = "";
                    String pdLat = "";
                    String pdLng = "";
                    dialog.dismiss();
                    LoginResponse resObj = response.body();
                    if (resObj.getStatus().equals("true")){
                        SharedPrefs.setLoggedIn(getApplicationContext(), true);
                        List<LoginResponse.DriverDetails> driverDetails = resObj.resp;
                        for (LoginResponse.DriverDetails details : driverDetails){
                            driverID += details.getDri_pkid();
                            driverSID += details.getDri_school_fkid();
                            driverNumber += details.getDri_number();
                            driverPhone += details.getDri_phone();
                            driverFname += details.getDri_fname();
                            driverLname += details.getDri_lname();
                            driverEmail += details.getDri_email();
                            driverPassword += details.getDr_password();
                            driverJdate += details.getDri_join_date();
                            driverDOB += details.getDri_dob();
                            driverAddress += details.getDri_address();
                            driverPostCode += details.getDri_postcode();
                            driverCity += details.getDri_city();
                            driverCountry += details.getDri_country();
                            driverCid += details.getDri_cid();
                            driverMdate += details.getDri_mdate();
                            driverMid += details.getDri_mid();
                            driverIsActive += details.getDri_isactive();
                            SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("driver_id", driverID);
                            editor.putString("driver_sid", driverSID);
                            editor.putString("driver_number", driverNumber);
                            editor.putString("driver_phone", driverPhone);
                            editor.putString("driver_fname", driverFname);
                            editor.putString("driver_lname", driverLname);
                            editor.putString("driver_email", driverEmail);
                            editor.putString("driver_password", driverPassword);
                            editor.putString("driver_jdate", driverJdate);
                            editor.putString("driver_dob", driverDOB);
                            editor.putString("driver_address", driverAddress);
                            editor.putString("driver_postcode", driverPostCode);
                            editor.putString("driver_city", driverCity);
                            editor.putString("driver_country", driverCountry);
                            editor.putString("driver_cid", driverCid);
                            editor.putString("driver_mdate", driverMdate);
                            editor.putString("driver_mid", driverMid);
                            editor.putString("driver_is_active", driverIsActive);
                            editor.commit();
                        }
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
