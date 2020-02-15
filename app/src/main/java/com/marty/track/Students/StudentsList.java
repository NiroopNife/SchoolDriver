package com.marty.track.Students;

import android.app.ProgressDialog;
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

public class StudentsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentsAdapter adapter;
    String driverid, driverschoolname;

    private List<StudentsModel> students;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        Bundle bundle = getIntent().getExtras();
        driverid = bundle.getString("driver_id");
        driverschoolname = bundle.getString("driver_sid");

        recyclerView = findViewById(R.id.students_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        students = new ArrayList<>();

        studentsList();

    }

    private void studentsList() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..!");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET,
                Constant.BASE_URL+"/studentlist/"+driverschoolname+"/"+driverid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("res");
                            for (int i=0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                StudentsModel studentList = new StudentsModel(
                                        object.getString("as_fname"),
                                        object.getString("as_lname"),
                                        object.getString("as_adminssion_no"),
                                        object.getString("as_class"),
                                        object.getString("as_section"),
                                        object.getString("as_roll_no"),
                                        object.getString("ap_guardian_name"),
                                        object.getString("ap_guardian_phone"),
                                        object.getString("as_address"),
                                        object.getString("as_city"),
                                        object.getString("as_country")

                                );
                                students.add(studentList);
                            }

                            adapter = new StudentsAdapter(students, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentsList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
