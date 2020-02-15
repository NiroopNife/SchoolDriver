package com.marty.track.ForgotPassword.PhaseTwo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.marty.track.Login.LoginActivity;
import com.marty.track.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    EditText confirmPassword;
    EditText newPassword;
    EditText receivedOTP;
    ImageButton resetPassword;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_otp);
        this.receivedOTP = (EditText) findViewById(R.id.etotp);
        this.newPassword = (EditText) findViewById(R.id.etnewpassword);
        this.confirmPassword = (EditText) findViewById(R.id.etconfirmpassword);
        this.resetPassword = (ImageButton) findViewById(R.id.reset);
        if (this.newPassword.getText().toString().equals(this.confirmPassword.getText().toString())) {
            this.resetPassword.setEnabled(true);
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        }
        this.resetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OTPActivity.this.passwordReset();
            }
        });
    }

    /* access modifiers changed from: private */
    public void passwordReset() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Comparing the OTP...");
        progressDialog.show();
        String otpReceived = this.receivedOTP.getText().toString().trim();
        String passworConfirm = this.confirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(otpReceived)) {
            this.receivedOTP.setError("Enter the Received OTP");
            this.receivedOTP.requestFocus();
            progressDialog.dismiss();
            return;
        }
        ((OPTService) OTPClient.forgotSecond().create(OPTService.class)).sendreceivedOTP(otpReceived, passworConfirm).enqueue(new Callback<OTPResponse>() {
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                String str = "Entered OTP is incorrect";
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (((OTPResponse) response.body()).getStatus().equals("true")) {
                        OTPActivity oTPActivity = OTPActivity.this;
                        oTPActivity.startActivity(new Intent(oTPActivity, LoginActivity.class));
                        return;
                    }
                    progressDialog.dismiss();
                    Toast.makeText(OTPActivity.this, str, Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.dismiss();
                Toast.makeText(OTPActivity.this, str, Toast.LENGTH_SHORT).show();
            }

            public void onFailure(Call<OTPResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OTPActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
