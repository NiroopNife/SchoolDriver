package com.marty.track.StudentsPickAndDrop.StudentsPick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marty.track.Constants.Constant;
import com.marty.track.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsPickActivity extends Activity {

    public static final String MY_PREFS_NAME = "SchoolPrefs";
    private RecyclerView recyclerView;
    private StudentsPickAdapter adapter;
    String driverid, driverschoolname;
    Context mCtx;
    private List<StudentsPickModel> students;
    private ClickListener listener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_pick);

        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        driverid = preferences.getString("driver_id", "");
        driverschoolname = preferences.getString("driver_sid", "");

        recyclerView = findViewById(R.id.students_pick_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        students = new ArrayList<>();

        studentsPick();


    }

    private void studentsPick(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...!");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET,
                Constant.Route_URL+driverschoolname+"/"+driverid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("res");
                            for (int i=0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                StudentsPickModel pickList = new StudentsPickModel(
                                        object.getString("as_fname"),
                                        object.getString("as_lname"),
                                        object.getString("as_class"),
                                        object.getString("as_section"),
                                        object.getString("pdloc_name"),
                                        object.getString("ap_guardian_phone"),
                                        object.getString("pdloc_latitude"),
                                        object.getString("pdloc_longitude")

                                );
                                students.add(pickList);
                            }
                            adapter = new StudentsPickAdapter(students, getApplicationContext(), listener);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentsPickActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

}
