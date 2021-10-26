package com.example.triageappintellisoft;

import static com.example.triageappintellisoft.VitalsForm.PATIENT_PK;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;
import com.example.triageappintellisoft.models.Patient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restvolley.MySingleton;

public class RegistrationPage extends AppCompatActivity {
    private ActivityRegistrationPageBinding binding;
    private MaterialTextView currentDateTV;
    private MaterialButton saveBtn;
    private TextInputEditText firstNameEditText, lastNameEditText, dayEditText, yearEditText;
    private Spinner monthSpinner, genderSpinner;
    private String firstName, lastName, dob;
    private Gender gender;
    private int currentDay, currentMonth, currentYear;
    private static final String[] MONTHS = new String[]{"Jan", "Feb", "March", "April", "May", "June",
            "July", "August", "Sep", "Oct", "Nov", "Dec"};
    private static String[] GENDERS = new String[]{"Male", "Female", "Prefer Not To Say"};

    private int patientPK;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private int monthIndex;


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
        saveBtn = binding.saveBtnReg;

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());

        String[] dayMonthYear = date.split("/");
        currentDay = Integer.parseInt(dayMonthYear[0]);
        currentMonth = Integer.parseInt(dayMonthYear[1]);
        currentYear = Integer.parseInt(dayMonthYear[2]);

        currentDateTV.setText(date);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TextInputEditText> editTexts = new ArrayList<>();
                Collections.addAll(editTexts, firstNameEditText, lastNameEditText, dayEditText, yearEditText);
                if (isFilled(editTexts)) {
                    firstName = firstNameEditText.getText().toString();
                    lastName = lastNameEditText.getText().toString();
                    String dd, mm, yy;
                    dd = dayEditText.getText().toString();
                    yy = yearEditText.getText().toString();
                    if (isValidDate(Integer.parseInt(dd), monthIndex, Integer.parseInt(yy))) {
                        mm = String.valueOf(monthIndex);
                        dob = yy + "-" + mm + "-" + dd;
                        saveToDB();
                    } else {
                        Toast.makeText(RegistrationPage.this, "The date of birth is invalid", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegistrationPage.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, MONTHS);
        monthSpinner.setAdapter(monthsAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthIndex = (i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        monthSpinner.setSelection(0);
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
        genderSpinner.setSelection(0);


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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.appbar_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_save:
//
//        }
//
//
//    }

    private boolean isValidDate(int day, int month, int year) {
        if (year > currentYear) {
            return false;
        } else if (year == currentYear) {
            if (month > currentMonth) {
                return false;
            } else if (month == currentMonth) {
                if (day > currentDay) {
                    return false;
                }
            }
        }

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return day > 0 && day <= 31;
        } else if (month == 2) {
            if (year % 4 == 0) {
                return day > 0 && day <= 29;
            } else {
                return day > 0 && day <= 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day > 0 && day <= 30;
        }

        return true;

    }

    public static boolean isFilled(List<TextInputEditText> editTexts) {
        for (TextInputEditText textInputEditText : editTexts) {
            if (textInputEditText.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }

    private void saveToDB() {

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


        String url = Nothing.URL + "patients/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, patientJsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            patientPK = response.getInt("id");
                            goToVitalsPage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        Toast.makeText(RegistrationPage.this,"Check your Internet",  Toast.LENGTH_SHORT).show();

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

        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }


}