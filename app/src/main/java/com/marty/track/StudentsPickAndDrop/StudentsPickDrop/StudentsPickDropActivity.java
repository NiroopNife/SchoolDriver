package com.marty.track.StudentsPickAndDrop.StudentsPickDrop;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marty.track.Constants.Constant;
import com.marty.track.R;
import com.marty.track.StudentsPickAndDrop.Route.GetRoute;
import com.marty.track.StudentsPickAndDrop.StudentsDrop.StudentsDropActivity;
import com.marty.track.StudentsPickAndDrop.StudentsPick.StudentsPickActivity;
import com.marty.track.StudentsPickAndDrop.StudentsPick.StudentsPickModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StudentsPickDropActivity extends TabActivity {

    String driverid, driverschoolname;
    Button getRoute;
    private List<StudentsPickDropModel> students;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pick_drop);

        Bundle bundle = getIntent().getExtras();
        driverid = bundle.getString("driver_id");
        driverschoolname = bundle.getString("driver_sid");


        TabHost tabHost = getTabHost();

        TabHost.TabSpec pickSpec = tabHost.newTabSpec("Pick");
        pickSpec.setIndicator("Students Pick");
        Intent pickIntent = new Intent(this, StudentsPickActivity.class);
        pickIntent.putExtra("driver_id", driverid);
        pickIntent.putExtra("driver_sid", driverschoolname);
        pickSpec.setContent(pickIntent);

        TabHost.TabSpec dropSpec = tabHost.newTabSpec("Drop");
        dropSpec.setIndicator("Students Drop");
        Intent dropIntent = new Intent(this, StudentsDropActivity.class);
        dropIntent.putExtra("driver_id", driverid);
        dropIntent.putExtra("driver_sid", driverschoolname);
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

    private void findRoute(){
        StringRequest request = new StringRequest(Request.Method.GET,
                Constant.Route_URL + "/" + driverschoolname + "/" + driverid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String pdlatitude = "";
                        String pdlongitude = "";
                        String schoollat = "";
                        String schoollng = "";
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("res");
                            for (int i=0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                StudentsPickDropModel pickDropModel = new StudentsPickDropModel(
                                        object.getString("school_pick_latitude"),
                                        object.getString("school_pick_longitude"),
                                        object.getString("pdloc_name"),
                                        object.getString("pdloc_latitude"),
                                        object.getString("pdloc_longitude")
                                );
                                schoollat += pickDropModel.getSchoolPDLat();
                                schoollng += pickDropModel.getSchoolPDLng();
                                System.out.println("Latitude : "+schoollat);
                                System.out.println("Longitude : "+schoollng);
                                pdlatitude += pickDropModel.getPdLatitude();
                                pdlongitude += pickDropModel.getPdLongitude();
                                Toast.makeText(StudentsPickDropActivity.this, pickDropModel.getPdName(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StudentsPickDropActivity.this, GetRoute.class);
                                intent.putExtra("pd_latitude", pdlatitude);
                                intent.putExtra("pd_longitude", pdlongitude);
                                intent.putExtra("school_lat", schoollat);
                                intent.putExtra("school_lng", schoollng);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentsPickDropActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


}
