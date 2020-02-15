package com.marty.track.Login;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.marty.track.Home.HomeActivity;
import com.marty.track.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    EditText editPassword, editUsername;
    TextView forgotPassword;
    ImageButton submit;
    String schoolID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        schoolID = bundle.getString("school_id");

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
                    dialog.dismiss();
                    LoginResponse resObj = response.body();
                    if (resObj.getStatus().equals("true")){
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
                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("driver_id", driverID);
                        intent.putExtra("driver_sid", driverSID);
                        intent.putExtra("driver_number", driverNumber);
                        intent.putExtra("driver_phone", driverPhone);
                        intent.putExtra("driver_fname", driverFname);
                        intent.putExtra("driver_lname", driverLname);
                        intent.putExtra("driver_email", driverEmail);
                        intent.putExtra("driver_password", driverPassword);
                        intent.putExtra("driver_jdate", driverJdate);
                        intent.putExtra("driver_dob", driverDOB);
                        intent.putExtra("driver_address", driverAddress);
                        intent.putExtra("driver_postcode", driverPostCode);
                        intent.putExtra("driver_city", driverCity);
                        intent.putExtra("driver_country", driverCountry);
                        intent.putExtra("driver_cid", driverCid);
                        intent.putExtra("driver_mdate", driverMdate);
                        intent.putExtra("driver_mid", driverMid);
                        intent.putExtra("driver_is_active", driverIsActive);
                        startActivity(intent);
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
