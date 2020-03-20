package com.marty.track.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marty.track.Constants.Constant;
import com.marty.track.Profile.ProfileActivity;
import com.marty.track.R;
import com.marty.track.School.SchoolActivity;
import com.marty.track.SendLocation.LocationService;
import com.marty.track.Students.StudentsList;
import com.marty.track.StudentsPickAndDrop.StudentsPickDrop.StudentsPickDropActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HomeActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "SchoolPrefs";
    ImageView logOut;
    TextView schoolName, tripText;
    Switch locationSwitch;
    SendCurrentLocation location;
    Button startLocation;
    String driverid, driverfname, driverlname, driverphone, driverdob, driveraddress, drivercity, driveremail, driverpassword,
            driverschoolname, drivernumber, driverjoiningdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        driverid = preferences.getString("driver_id", "");
        driverfname = preferences.getString("driver_fname", "");
        driverlname = preferences.getString("driver_lname", "");
        driverphone = preferences.getString("driver_phone", "");
        driverdob = preferences.getString("driver_dob", "");
        driveraddress = preferences.getString("driver_address", "");
        drivercity = preferences.getString("driver_city", "");
        driveremail = preferences.getString("driver_email", "");
        driverpassword = preferences.getString("driver_password", "");
        driverschoolname = preferences.getString("driver_sid", "");
        drivernumber = preferences.getString("driver_sid", "");
        driverjoiningdate = preferences.getString("driver_jdate", "");


        startLocation = findViewById(R.id.startlocation);

        tripText = findViewById(R.id.triptext);

        schoolName = findViewById(R.id.school_name);
        schoolName.setText(driverschoolname);

        logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefs.setLoggedIn(getApplicationContext(), false);
                SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(HomeActivity.this, SchoolActivity.class));
            }
        });

        locationSwitch = findViewById(R.id.starttrip);
        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tripText.setText("Trip has been started, Drive Saftely!");
                    tripText.setVisibility(View.VISIBLE);
                    startTrip();

                } else {
                    tripText.setText("Trip has not yet started!");
                    tripText.setVisibility(View.VISIBLE);
                    unregisterService();
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });


    }

    public void profileClicked(View view) {
        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
    }


    public void studentsListClicked(View view) {
        startActivity(new Intent(HomeActivity.this, StudentsList.class));
    }

    public void studentsPandDclicked(View view) {
        Intent intent = new Intent(HomeActivity.this, StudentsPickDropActivity.class);
        intent.putExtra("driver_id", driverid);
        intent.putExtra("driver_sid", driverschoolname);
        startActivity(intent);
    }

    void startTrip() {
        location = new SendCurrentLocation();
        IntentFilter filter = new IntentFilter("ACT_LOC");
        registerReceiver(location, filter);
        Intent intent = new Intent(HomeActivity.this, LocationService.class);
        startService(intent);
    }

    void unregisterService(){
        unregisterReceiver(location);
    }

    public class SendCurrentLocation extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACT_LOC")) {
                double lat = intent.getDoubleExtra("latitude", 0f);
                double lng = intent.getDoubleExtra("longitude", 0f);
                System.out.println(lat + lng);
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("latitude", lat);
                    jsonBody.put("longitude", lng);
                    jsonBody.put("loc_driver_id", driverid);
                    jsonBody.put("loc_school_id", driverschoolname);
                    final String mRequestBody = jsonBody.toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Track_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("LOG_RESPONSE", response);
                                    Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };
                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}