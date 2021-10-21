package com.example.triageappintellisoft;

import static com.example.triageappintellisoft.VitalsForm.PATIENT_PK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;
import com.example.triageappintellisoft.models.Patient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import restvolley.MySingleton;

public class RegistrationPage extends AppCompatActivity {
    private ActivityRegistrationPageBinding binding;
    private MaterialTextView currentDateTV;
    private TextInputEditText firstNameEditText, lastNameEditText, dayEditText, yearEditText;
    private Spinner monthSpinner, genderSpinner;
    private String firstName, lastName, dob, monthString;
    private Gender gender;
    private static final String[] MONTHS = new String[]{"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    private static String[] GENDERS = new String[]{"Male", "Female", "Prefer Not To Say"};

    private int patientPK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        currentDateTV = binding.currentDateTv;
        firstNameEditText = binding.textInputEditTextFirstName;
        lastNameEditText = binding.textInputEditTextLastName;
        dayEditText = binding.dayEditText;
        monthSpinner = binding.monthSpinner;
        genderSpinner = binding.genderSpinner;
        yearEditText = binding.yearEditText;

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, MONTHS);
        monthSpinner.setAdapter(monthsAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthString = String.valueOf(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, GENDERS);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        gender = Gender.MALE;
                        break;
                    case 1:
                        gender = Gender.FEMALE;
                        break;
                    case 2:
                        gender = Gender.NO_SAY;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void goToVitalsPage() {
        Intent intent = new Intent(RegistrationPage.this, VitalsForm.class);
        intent.putExtra(PATIENT_PK, patientPK);
        startActivity(intent);
        finish();
    }

    private enum Gender {
        MALE("M"),
        FEMALE("F"),
        NO_SAY("N");
        String genderInitials;

        Gender(String genderInitials) {
            this.genderInitials = genderInitials;
        }

        public String getGenderInitials() {
            return genderInitials;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //TODO check that all fields have been filled
                saveToDB();
                goToVitalsPage();
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void saveToDB() {
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        String dd, mm, yy;
        dd = dayEditText.getText().toString();
        yy = yearEditText.getText().toString();
        mm = monthString;
        dob = yy + "-" + mm + "-" + dd;
        String genderString = gender.getGenderInitials();
        Patient patient = new Patient(firstName, lastName, dob, genderString);


        Gson gson = new Gson();
        String json = gson.toJson(patient);
        JSONObject patientJsonObject = null;
        try {
            patientJsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO set the correct endpoint below
        String url = Nothing.URL + "patients";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, patientJsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            patientPK = response.getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

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

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        //TODO post and return primary key

    }


}