package com.marty.track.SendLocation;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.marty.track.Constants.Constant;
import com.marty.track.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class LocationPosting extends AppCompatActivity {

    String driverid, driverschoolname;
    Context mCtx;
    private LocationBroadcastReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);

        Toast.makeText(mCtx, "Location Posting", Toast.LENGTH_SHORT).show();

        Button stopLocation = findViewById(R.id.stop);
        stopLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx.unregisterReceiver(receiver);
            }
        });

        Bundle bundle = getIntent().getExtras();
        driverid = bundle.getString("driver_id");
        driverschoolname = bundle.getString("driver_sid");

//        SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
//        driverschoolname = sharedPreferences.getString("ad_pkid", "");

        if (Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                startService();
            }
        } else {
            startService();
        }

    }

    void startService(){
        receiver = new LocationBroadcastReceiver();
        IntentFilter filter = new IntentFilter("ACT_LOC");
        registerReceiver(receiver, filter);
        Intent intent = new Intent(LocationPosting.this, LocationService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startService();
                } else {
                    Toast.makeText(this, "Permissions Not Granted!", Toast.LENGTH_LONG).show();
                }
        }
    }

    public class LocationBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACT_LOC")){
                double lat = intent.getDoubleExtra("latitude", 0f);
                double lng = intent.getDoubleExtra("longitude", 0f);

                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(LocationPosting.this);
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
//                                    Toast.makeText(LocationPosting.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("LOG_RESPONSE", error.toString());
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
