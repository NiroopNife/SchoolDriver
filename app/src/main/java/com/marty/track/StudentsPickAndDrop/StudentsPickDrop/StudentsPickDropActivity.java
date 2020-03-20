package com.marty.track.StudentsPickAndDrop.StudentsPickDrop;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marty.track.Constants.Constant;
import com.marty.track.R;
import com.marty.track.StudentsPickAndDrop.StudentsDrop.StudentsDropActivity;
import com.marty.track.StudentsPickAndDrop.StudentsPick.StudentsPickActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsPickDropActivity extends TabActivity {

    public static final String MY_PREFS_NAME = "SchoolPrefs";
    String driverid, driverschoolname;
    Button getRoute;
    private List<StudentsPickDropModel> students;
    SharedPreferences sharedPreferences;
    int LocationCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pick_drop);

        sharedPreferences = getSharedPreferences("location", 0);
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        driverid = preferences.getString("driver_id", "");
        driverschoolname = preferences.getString("ad_pkid", "");

        TabHost tabHost = getTabHost();

        TabHost.TabSpec pickSpec = tabHost.newTabSpec("Pick");
        pickSpec.setIndicator("Students Pick");
        Intent pickIntent = new Intent(this, StudentsPickActivity.class);
        pickSpec.setContent(pickIntent);

        TabHost.TabSpec dropSpec = tabHost.newTabSpec("Drop");
        dropSpec.setIndicator("Students Drop");
        Intent dropIntent = new Intent(this, StudentsDropActivity.class);
        dropSpec.setContent(dropIntent);

        tabHost.addTab(pickSpec);
        tabHost.addTab(dropSpec);

        getRoute = findViewById(R.id.getroute);
        getRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRoute();
            }
        });
    }

    private void findRoute() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Constant.Route_URL + "/" + driverschoolname + "/" + driverid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String pdlatitude, pdlongitude, location;
                        ArrayList<String> pd_lat= new ArrayList<String>();
                        ArrayList<String> pd_lng= new ArrayList<String>();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray result = object.getJSONArray("res");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject jsonObject = result.getJSONObject(i);
                                pdlatitude = jsonObject.getString("pdloc_latitude");
                                pdlongitude = jsonObject.getString("pdloc_longitude");
                                location = jsonObject.getString("pdloc_name");
                                pd_lat.add(jsonObject.getString("pdloc_latitude"));
                                pd_lng.add(jsonObject.getString("pdloc_longitude"));
                                SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("pdloc_latitude", pdlatitude);
                                editor.putString("pdloc_longitude", pdlongitude);
                                editor.putString("pd_lat", String.valueOf(pd_lat));
                                editor.putString("pd_lng", String.valueOf(pd_lng));
                                Intent intent = new Intent(StudentsPickDropActivity.this, GetRoute.class);
                                intent.putExtra("pdloc_latitude", pdlatitude);
                                intent.putExtra("driver_id", driverid);
                                intent.putExtra("driver_sid", driverschoolname);
                                intent.putExtra("pdloc_longitude", pdlongitude);
                                intent.putExtra("pd_lat", pd_lat);
                                intent.putExtra("pd_lng", pd_lng);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(StudentsPickDropActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);

    }


}
