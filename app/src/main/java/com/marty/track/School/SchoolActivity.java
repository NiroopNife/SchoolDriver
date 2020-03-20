package com.marty.track.School;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.marty.track.Login.LoginActivity;
import com.marty.track.R;
import com.marty.track.StudentsPickAndDrop.StudentsPickDrop.GetRoute;
import com.marty.track.StudentsPickAndDrop.StudentsPickDrop.StudentsPickDropActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ImageButton nextPage;
    EditText schoolName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolname);

        schoolName = findViewById(R.id.etschoolname);

        nextPage = findViewById(R.id.next);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSchoolID();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }

    private void sendSchoolID(){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait....!");
        dialog.show();

        final String schoolname = schoolName.getText().toString().trim();

        if (TextUtils.isEmpty(schoolname)){
            schoolName.setError("Please enter your School ID");
            schoolName.requestFocus();
            dialog.dismiss();
            return;
        }

        Retrofit retrofit = SchoolClient.sendSchoolname();
        SchoolService service = retrofit.create(SchoolService.class);
        Call<SchoolResponse> call = service.sendSchoolname(schoolname);
        call.enqueue(new Callback<SchoolResponse>() {
            @Override
            public void onResponse(Call<SchoolResponse> call, Response<SchoolResponse> response) {
                if (response.isSuccessful()){
                    String schoolid = "";
                    String schoolLat = "";
                    String schoolLng = "";
                    dialog.dismiss();
                    SchoolResponse resObj = response.body();
                    if (resObj.getStatus().equals("true")){
                        List<SchoolResponse.SchoolDetails> schoolDetails = resObj.res;
                        for (SchoolResponse.SchoolDetails details : schoolDetails){
                            schoolid += details.getSchool_id();
                            schoolLat += details.getSchool_pick_latitude();
                            schoolLng += details.getSchool_pick_longitude();
                            System.out.println("School ID : "+schoolid);
                            SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("school_pick_latitude", schoolLat);
                            editor.putString("school_pick_longitude", schoolLng);
                            editor.putString("ad_pkid", schoolid);
                            editor.commit();
                        }
                        Intent intent = new Intent(SchoolActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onFailure(Call<SchoolResponse> call, Throwable t) {
                dialog.dismiss();
                System.out.println(t);
                Toast.makeText(SchoolActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
