package com.marty.track.ForgotPassword.PhaseOne;

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

import com.marty.track.ForgotPassword.PhaseTwo.OTPActivity;
import com.marty.track.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailActivity extends AppCompatActivity {

    ImageButton nextStep;
    EditText registeredEmail;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_email);
        this.registeredEmail = (EditText) findViewById(R.id.etregemail);
        this.nextStep = (ImageButton) findViewById(R.id.reset);
        this.nextStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EmailActivity.this.goToNextStep();
            }
        });
    }

    /* access modifiers changed from: private */
    public void goToNextStep() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending OTP to entered Email..");
        progressDialog.show();
        String regEmail = this.registeredEmail.getText().toString().trim();
        if (TextUtils.isEmpty(regEmail)) {
            this.registeredEmail.setError("Enter your registered Email");
            this.registeredEmail.requestFocus();
            progressDialog.dismiss();
            return;
        }
        ((EmailService) EmailClient.forgotFirst().create(EmailService.class)).sendRegisteredEmail(regEmail).enqueue(new Callback<EmailResponse>() {
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                String str = "The username or password is incorrect";
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (((EmailResponse) response.body()).getStatus().equals("true")) {
                        EmailActivity emailActivity = EmailActivity.this;
                        emailActivity.startActivity(new Intent(emailActivity, OTPActivity.class));
                        return;
                    }
                    progressDialog.dismiss();
                    Toast.makeText(EmailActivity.this, str, Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.dismiss();
                Toast.makeText(EmailActivity.this, str, Toast.LENGTH_SHORT).show();
            }

            public void onFailure(Call<EmailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EmailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
