package com.example.triageappintellisoft;

import static com.example.triageappintellisoft.VitalsForm.PATIENT_PK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triageappintellisoft.databinding.ActivityMainBinding;
import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restvolley.MySingleton;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MaterialTextView currentDateTV;
    private FloatingActionButton addFab;
    public static final String NORMAL = "Normal";
    public static final String UNDERWEIGHT = "Underweight";
    public static final String OVERWEIGHT = "Overweight";
    public static final double OVER_WEIGHT_THRESHOLD = 25;
    public static final double UNDER_WEIGHT_THRESHOLD = 18.5;


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    MyRecyclerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        currentDateTV = binding.currentDateTv;

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        currentDateTV.setText(date);


        List<String> patientsNames = new ArrayList<>();
        List<String> patientsAges = new ArrayList<>();
        List<String> patientsBMIs = new ArrayList<>();
        myAdapter = new MyRecyclerAdapter(patientsNames, patientsAges, patientsBMIs);

        addFab = binding.addPatientButton;
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);
                finish();
            }
        });

        String url = Nothing.URL + "patients-brief/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //The JSON has 'first_name','last_name','dob','vitals','age'
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject patientObject;
                            try {
                                patientObject = response.getJSONObject(i);
                                String firstName = patientObject.getString("first_name");
                                String lastName = patientObject.getString("last_name");
                                String fullName = firstName + " " + lastName;
                                String age = patientObject.getString("age");
                                double bmi=18;
                                if(patientObject.getJSONArray("vitals").length()>0){
                                    bmi = patientObject.getJSONArray("vitals").getDouble(0);
                                }


                                String bmiStatus;
                                if (bmi < UNDER_WEIGHT_THRESHOLD) {
                                    bmiStatus = UNDERWEIGHT;
                                } else if (bmi >= UNDER_WEIGHT_THRESHOLD && bmi < OVER_WEIGHT_THRESHOLD) {
                                    bmiStatus = NORMAL;
                                } else {
                                    bmiStatus = OVERWEIGHT;
                                }
                                patientsNames.add(fullName);
                                patientsAges.add(age);
                                patientsBMIs.add(bmiStatus);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        myAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(MainActivity.this, "Check your Internet", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String auth = "Token " + Nothing.TOKEN;
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonArrayRequest);


        RecyclerView rv = binding.patientsRecyclerView;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myAdapter);
    }
}
