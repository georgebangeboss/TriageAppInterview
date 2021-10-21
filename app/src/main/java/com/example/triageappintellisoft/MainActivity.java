package com.example.triageappintellisoft;

import static com.example.triageappintellisoft.VitalsForm.PATIENT_PK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restvolley.MySingleton;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MaterialTextView currentDateTV;
    private FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO add current date to all involved screens
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        currentDateTV = binding.currentDateTv;
        addFab = binding.addPatientButton;
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);
                finish();
            }
        });

        //TODO get patient details from api and write the list below
        String url = Nothing.URL + "patient-short";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //'first_name','last_name','dob','vitals','age'
                        for(int i=0;i<response.length();i++){
                            JSONObject patientObject=null;
                            try {
                                patientObject=response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String auth = "Token " + Nothing.TOKEN;
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
        List<String> patientsList = new ArrayList<>();


        MyRecyclerAdapter myAdapter = new MyRecyclerAdapter(patientsList);
        RecyclerView rv = binding.patientsRecyclerView;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myAdapter);
    }
}
