package com.example.triageappintellisoft;

import static com.example.triageappintellisoft.RegistrationPage.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;
import com.example.triageappintellisoft.databinding.ActivityVitalsFormBinding;
import com.example.triageappintellisoft.models.Vital;
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


public class VitalsForm extends AppCompatActivity {
    private ActivityVitalsFormBinding binding;
    private TextInputEditText heightEditText, weightEditText;
    private MaterialTextView currentDateTV, bmiTV;
    private MaterialButton saveBtn;

    public static final String FORM_TITLE = "com.example.triageappintellisoft.FORM_TITLE";
    public static final String PATIENT_PK = "com.example.triageappintellisoft.PATIENT_PK";
    private double bmi, height, weight;
    private String dob;
    private boolean weightFilled = false;
    boolean heightFilled = false;
    private int patientPK;
    public static final double OVERWEIGHT = 25.0;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVitalsFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        patientPK = intent.getIntExtra(PATIENT_PK, 1000000);

        currentDateTV = binding.currentDateTv;
        bmiTV = binding.bmiTv;
        saveBtn = binding.saveBtnForm;
        heightEditText = binding.textInputEditTextHeight;
        weightEditText = binding.textInputEditTextWeight;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        currentDateTV.setText(date);


        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                heightFilled = true;
                height = Double.parseDouble(charSequence.toString());
                System.out.println("Height = "+height);
                if (weightFilled && height != 0) {
                    bmi = weight / (height * height / 10000);
                    bmi=Math.round(bmi*100)/100;
                    bmiTV.setText(String.valueOf(bmi));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                weightFilled = true;
                weight = Double.parseDouble(charSequence.toString());
                System.out.println("Weight = "+weight);
                if (heightFilled && height != 0) {
                    bmi = weight / (height * height / 10000);
                    bmi=Math.round(bmi*100)/100;
                    bmiTV.setText(String.valueOf(bmi));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TextInputEditText> editTexts = new ArrayList<>();
                Collections.addAll(editTexts, heightEditText, weightEditText);
                if (isFilled(editTexts)) {
                    if (isValidWeightHeight()) {
                        saveToDB();
                    } else {
                        Toast.makeText(VitalsForm.this, "Can't be zero", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(VitalsForm.this, "Fill both height and weight fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToVisitForms() {
        Intent intent = new Intent(VitalsForm.this, VisitForm.class);
        String formTitle = (bmi >= OVERWEIGHT) ? "FORM B - OVERWEIGHT" : "FORM A";
        intent.putExtra(FORM_TITLE, formTitle);
        intent.putExtra(PATIENT_PK, patientPK);
        startActivity(intent);
        finish();
    }



    private boolean isValidWeightHeight() {
        double height = Double.parseDouble(heightEditText.getText().toString());
        double weight = Double.parseDouble(weightEditText.getText().toString());
        return weight > 0 && height > 0;
    }

    private void saveToDB() {
        Vital vital = new Vital(height, weight, bmi, patientPK);
        Gson gson = new Gson();
        String json = gson.toJson(vital);

        JSONObject vitalJsonObject = null;
        try {
            vitalJsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = Nothing.URL + "vitals/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, vitalJsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        goToVisitForms();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VitalsForm.this,"Check your Internet",  Toast.LENGTH_SHORT).show();


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